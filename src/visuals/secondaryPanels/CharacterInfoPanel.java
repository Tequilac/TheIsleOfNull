package visuals.secondaryPanels;

import entities.Character;
import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CharacterInfoPanel extends JPanel
{
    private  Game game;

    private Frame frame;

    private Character character;

    private JLabel name;

    private JLabel characterHealthStats;

    private JLabel characterManaStats;

    public CharacterInfoPanel(Game game, Frame frame, Character character)
    {
        super();
        this.game = game;
        this.frame = frame;
        this.character = character;
        setPreferredSize(new Dimension(300, 200));
        setLayout(null);
    }

    public void drawCharacterInfo(Graphics g)
    {
        if(name == null)
        {
            name = new JLabel(character.getName());
            name.setBounds(10,20, 120,20);
            name.addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent mouseEvent)
                {
                    game.setCurrentCharacter(character);
                    frame.openTeamView();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent)
                {

                }
            });
            add(name);


            characterHealthStats = new JLabel();
            characterHealthStats.setBounds(140,45,100,20);
            add(characterHealthStats);

            characterManaStats = new JLabel();
            characterManaStats.setBounds(140,70,100,20);
            add(characterManaStats);
        }

        g.setColor(new Color(0, 255, 0));
        g.drawRect(10,45,280,20);
        g.fillRect(10,45,280*character.getHealth()/character.getMaxHealth(),20);

        g.setColor(new Color(0, 0, 255));
        g.drawRect(10,70,280,20);
        g.fillRect(10,70,280*character.getMana()/character.getMaxMana(),20);

        characterHealthStats.setText(character.getHealth() + "/" + character.getMaxHealth());

        characterManaStats.setText(character.getMana() + "/" + character.getMaxMana());
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawCharacterInfo(g);
    }
}
