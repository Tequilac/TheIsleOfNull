package visuals.primaryPanels;

import inhabitants.Inhabitant;
import inhabitants.Merchant;
import inhabitants.Trainer;
import main.Game;
import map.Chest;
import map.Vector2d;
import map.districts.AbstractHiddenDistrict;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.QuestGiver;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapPanel extends PrimaryPanel
{
    private final Image townImage;

    private final Image dungeonImage;

    private final Image chestImage;

    private final Image questGiverImage;

    private final Image merchantImage;

    private final Image trainerImage;

    private final Image[] worldTileGraphics;

    private final Image[] townTileGraphics;

    private final Image[] dungeonTileGraphics;

    private final BufferedImage[] teamImages = new BufferedImage[4];

    private final BufferedImage[] enemiesImages = new BufferedImage[4];

    private final Image mapBackgroundImage;

    private JLabel[] enemiesStats;

    public MapPanel(Game game, Frame frame)
    {
        super(game, frame);


        worldTileGraphics = new Image[10];
        ImageIcon iiWater = new ImageIcon("res/graphics/worldGraphics/water.png");
        worldTileGraphics[0] = iiWater.getImage();
        ImageIcon iiGrass = new ImageIcon("res/graphics/worldGraphics/grass.png");
        worldTileGraphics[1] = iiGrass.getImage();
        ImageIcon iiSand = new ImageIcon("res/graphics/worldGraphics/sand.png");
        worldTileGraphics[2] = iiSand.getImage();
        ImageIcon iiTeam = new ImageIcon("res/graphics/team.png");
        ImageIcon iiEnemies = new ImageIcon("res/graphics/enemy.png");

        townTileGraphics = new Image[10];
        townTileGraphics[0] = iiGrass.getImage();
        ImageIcon iiHouse = new ImageIcon("res/graphics/worldGraphics/house.png");
        townTileGraphics[1] = iiHouse.getImage();

        dungeonTileGraphics = new Image[10];
        ImageIcon iiWall = new ImageIcon("res/graphics/worldGraphics/dungeon_wall.png");
        dungeonTileGraphics[0] = iiWall.getImage();
        ImageIcon iiFloor = new ImageIcon("res/graphics/worldGraphics/dungeon_floor.png");
        dungeonTileGraphics[1] = iiFloor.getImage();
        ImageIcon iiBlackFloor = new ImageIcon("res/graphics/worldGraphics/dungeon_black_floor.png");
        dungeonTileGraphics[2] = iiBlackFloor.getImage();

        ImageIcon iiTown = new ImageIcon("res/graphics/worldGraphics/town.png");
        townImage = iiTown.getImage();
        ImageIcon iiDungeon = new ImageIcon("res/graphics/worldGraphics/dungeon.png");
        dungeonImage = iiDungeon.getImage();

        teamImages[0] = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = teamImages[0].createGraphics();
        bGr.drawImage(iiTeam.getImage(), 0, 0, null);
        bGr.dispose();

        enemiesImages[0] = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        bGr = enemiesImages[0].createGraphics();
        bGr.drawImage(iiEnemies.getImage(), 0, 0, null);
        bGr.dispose();

        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians (90), 10, 10);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        for (int i = 1; i < 4; i++)
        {
            teamImages[i] = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            bGr = teamImages[i].createGraphics();
            bGr.drawImage(teamImages[i], 0, 0, null);
            teamImages[i] = (op.filter(teamImages[i-1],null));
            bGr.dispose();

            enemiesImages[i] = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            bGr = enemiesImages[i].createGraphics();
            bGr.drawImage(enemiesImages[i], 0, 0, null);
            enemiesImages[i] = (op.filter(enemiesImages[i-1],null));
            bGr.dispose();
        }

        ImageIcon iiChest = new ImageIcon("res/graphics/chest.png");
        chestImage = iiChest.getImage();

        ImageIcon iiQuestGiver = new ImageIcon("res/graphics/quest_giver.png");
        questGiverImage = iiQuestGiver.getImage();

        ImageIcon iiMerchant = new ImageIcon("res/graphics/merchant.png");
        merchantImage = iiMerchant.getImage();

        ImageIcon iiTrainer = new ImageIcon("res/graphics/trainer.png");
        trainerImage = iiTrainer.getImage();

        ImageIcon iiBackground = new ImageIcon("res/graphics/map_background.png");
        mapBackgroundImage = iiBackground.getImage();

        setEnemiesButtons();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(mapBackgroundImage, 0, 0, this);

        drawDistrict(g);

        drawEntities(g);

        setEnemiesButtons();
    }

    public void drawDistrict(Graphics g)
    {
        for (int i = 0; i < game.getCurrentDistrict().getWidth(); i++)
        {
            for (int j = 0; j < game.getCurrentDistrict().getHeight(); j++)
            {
                if(game.getCurrentDistrict() instanceof World)
                {
                    if(((World) game.getCurrentDistrict()).getVisibleTiles()[j][i])
                        g.drawImage(worldTileGraphics[game.getCurrentDistrict().getTiles()[j][i]], i*20, j*20, this);
                }
                else if(game.getCurrentDistrict() instanceof Town)
                {
                    g.drawImage(townTileGraphics[game.getCurrentDistrict().getTiles()[j][i]], i*20, j*20, this);
                }
                else
                {
                    if(((Dungeon) game.getCurrentDistrict()).getVisibleTiles()[j][i])
                        g.drawImage(dungeonTileGraphics[game.getCurrentDistrict().getTiles()[j][i]], i*20, j*20, this);
                }
            }
        }

        if(game.getCurrentDistrict() instanceof World)
        {
            Vector2d town = ((World) game.getCurrentDistrict()).getTownEntrance();
            if(town != null && ((World) game.getCurrentDistrict()).isVisible(town))
                g.drawImage(townImage, town.getX()*20, town.getY()*20, this);
            Vector2d dungeon = ((World) game.getCurrentDistrict()).getDungeonEntrance();
            if(dungeon != null && ((World) game.getCurrentDistrict()).isVisible(dungeon))
                g.drawImage(dungeonImage, dungeon.getX()*20, dungeon.getY()*20, this);
        }

        if(game.getCurrentDistrict() instanceof Town)
        {
            ArrayList<Inhabitant> inhabitants = ((Town) game.getCurrentDistrict()).getInhabitants();
            for(Inhabitant inhabitant : inhabitants)
            {
                if(inhabitant instanceof Merchant)
                {
                    g.drawImage(merchantImage, inhabitant.getPosition().getX()*20, inhabitant.getPosition().getY()*20, this);
                }
                if(inhabitant instanceof Trainer)
                {
                    g.drawImage(trainerImage, inhabitant.getPosition().getX()*20, inhabitant.getPosition().getY()*20, this);
                }
            }
        }

        ArrayList<Chest> chests = game.getCurrentDistrict().getChests();
        if(chests != null)
        {
            for (Chest chest : chests)
            {
                Vector2d position = chest.getPosition();
                if(game.getCurrentDistrict() instanceof World)
                {
                    if (((World) game.getCurrentDistrict()).getVisibleTiles()[position.getY()][position.getX()])
                        g.drawImage(chestImage, position.getX() * 20, position.getY() * 20, this);
                }
                else if (game.getCurrentDistrict() instanceof Town)
                {
                    g.drawImage(chestImage, position.getX() * 20, position.getY() * 20, this);
                }
                else
                {
                    if (((Dungeon) game.getCurrentDistrict()).getVisibleTiles()[position.getY()][position.getX()])
                        g.drawImage(chestImage, position.getX() * 20, position.getY() * 20, this);
                }
            }
        }

        ArrayList<QuestGiver> questGivers = game.getCurrentDistrict().getQuestGivers();
        if (questGivers != null)
        {
            for (QuestGiver questGiver : questGivers)
            {
                Vector2d position = questGiver.getPosition();
                if (game.getCurrentDistrict() instanceof World)
                {
                    if (((World) game.getCurrentDistrict()).getVisibleTiles()[position.getY()][position.getX()])
                        g.drawImage(questGiverImage, position.getX() * 20, position.getY() * 20, this);
                }
                else
                {
                    g.drawImage(questGiverImage, position.getX() * 20, position.getY() * 20, this);
                }
            }
        }
    }

    public void drawEntities(Graphics g)
    {
        int num;
        switch (game.getTeam().getMapDirection())
        {
            case North: num = 0;
            break;
            case East: num = 1;
            break;
            case South: num = 2;
            break;
            default: num = 3;
        }
        Vector2d position = game.getTeam().getPosition();
        g.drawImage(teamImages[num], position.getX()*20, position.getY()*20, this);

        for (int i = 0; i < game.getCurrentDistrict().getEnemies().size(); i++)
        {
            position = game.getCurrentDistrict().getEnemies().get(i).getPosition();
            if(((World) game.getCurrentDistrict()).isVisible(position))
            {
                switch (game.getCurrentDistrict().getEnemies().get(i).getMapDirection())
                {
                    case North: num = 0;
                        break;
                    case East: num = 1;
                        break;
                    case South: num = 2;
                        break;
                    default: num = 3;
                }
                g.drawImage(enemiesImages[num], position.getX()*20, position.getY()*20, this);
            }
        }
    }

    public void setEnemiesButtons()
    {
        if(enemiesStats != null)
        {
            for (JLabel enemiesStat : enemiesStats)
            {
                if (enemiesStat != null)
                {
                    remove(enemiesStat);
                    enemiesStat.removeMouseListener(enemiesStat.getMouseListeners()[0]);
                }
            }
        }
        enemiesStats = new JLabel[game.getCurrentDistrict().getEnemies().size()];
        for (int i = 0; i < game.getCurrentDistrict().getEnemies().size(); i++)
        {
            Vector2d position = game.getCurrentDistrict().getEnemies().get(i).getPosition();
            if(game.getCurrentDistrict() instanceof AbstractHiddenDistrict && ((AbstractHiddenDistrict) game.getCurrentDistrict()).isVisible(position))
            {
                enemiesStats[i] = new JLabel();
                enemiesStats[i].setFocusable(false);

                enemiesStats[i].setBounds(position.getX()*20, position.getY()*20, 20, 20);
                int finalI = i;
                enemiesStats[i].addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent)
                    {
                        game.setChosenEnemies(finalI);
                    }
                });
                add(enemiesStats[i]);
            }
        }
    }
}
