package visuals;

import model.game.Game;
import model.map.MoveDirection;
import visuals.primaryPanels.*;
import visuals.primaryPanels.teamPanels.TeamPanel;
import visuals.secondaryPanels.BlankPanel;
import visuals.secondaryPanels.CharactersInfoPanel;
import visuals.secondaryPanels.OptionsPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Frame extends JFrame implements KeyListener
{
    private MainPanel mainPanel;

    private Game game;

    public Frame(Game game)
    {
        super();
        addKeyListener(this);
        setFocusable(true);

        //game.setFrame(this);
        this.game = game;
        mainPanel = new MainPanel(this);
        add(mainPanel);
        openStartingPanel();
    }

    public void openStartingPanel()
    {

        mainPanel.addPrimaryPanel(new StartingPanel(game, this));
        setResizable(false);
        pack();

        setTitle("The Isle of Null");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openCharacterCreationPanel()
    {
        mainPanel.addPrimaryPanel(new CharacterCreationPanel(game, this));
        setResizable(false);
        pack();

        setTitle("The Isle of Null");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openSaveGamesPanel()
    {
        mainPanel.addPrimaryPanel(new SaveGamesPanel(game, this));
        setResizable(false);
        pack();

        setTitle("The Isle of Null");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void exitGame()
    {
        dispose();
    }

    public void openMapPanel()
    {
        game.update();
        mainPanel.addPrimaryPanel(new MapPanel(game, this));
        mainPanel.addLeftPanel(new OptionsPanel(game, this));
        mainPanel.addRightPanel(new CharactersInfoPanel(game, this));

        setResizable(false);
        pack();
        setTitle("The Isle of Null");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void openChest()
    {
        //mainPanel.addPrimaryPanel(new ChestPanel(game, game.getOpenedChest(), this));
        pack();
    }

    public void meetQuestGiver()
    {
        //mainPanel.addPrimaryPanel(new QuestsPanel(game, game.getMetQuestGiver(), this));
        pack();
    }

    public void meetInhabitant()
    {
//        if(game.getMetInhabitant() instanceof Merchant)
//            mainPanel.addPrimaryPanel(new MerchantPanel(game,this, (Merchant) game.getMetInhabitant()));
//        else
//            mainPanel.addPrimaryPanel(new TrainerPanel(game, this, (Trainer) game.getMetInhabitant()));
//        pack();
    }

    public void toggleTeamView()
    {
        if(mainPanel.getPrimaryPanel() instanceof TeamPanel)
            openMapPanel();
        else
            openTeamView(0);
    }

    public void openTeamView(Integer currentTab)
    {
        mainPanel.addPrimaryPanel(new TeamPanel(game, this, currentTab));
        pack();
    }

    public void closeLeftPanel()
    {
        mainPanel.addLeftPanel(new BlankPanel(game, this));
        pack();
    }

    public void closeRightPanel()
    {
        mainPanel.addRightPanel(new BlankPanel(game, this));
        pack();
    }

    public void updateEnemies()
    {
        mainPanel.updateEnemies();
    }

    public void updateButtons(boolean onObject, boolean onEntrance)
    {
        mainPanel.updateButtons(onObject, onEntrance);
    }

    public Integer getCurrentTab()
    {
        if(mainPanel.getPrimaryPanel() instanceof TeamPanel)
            return ((TeamPanel) mainPanel.getPrimaryPanel()).getCurrentTab();
        return null;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if(mainPanel.mapOpened())
        {
            MoveDirection chosenDirection = null;
            switch (keyEvent.getKeyCode())
            {
                case KeyEvent.VK_W:
                    chosenDirection = MoveDirection.FRONT;
                    break;
                case KeyEvent.VK_A:
                    chosenDirection = MoveDirection.LEFT;
                    break;
                case KeyEvent.VK_D:
                    chosenDirection = MoveDirection.RIGHT;
                    break;
                case KeyEvent.VK_S:
                    chosenDirection = MoveDirection.BACK;
                    break;
                case KeyEvent.VK_Q:
                    chosenDirection = MoveDirection.TURN_LEFT;
                    break;
                case KeyEvent.VK_E:
                    chosenDirection = MoveDirection.TURN_RIGHT;
                    break;
            }
            if(chosenDirection != null)
            {
                game.tick(chosenDirection);
            }
            game.update();
            mainPanel.repaint();
        }
    }

    public Game getGame()
    {
        return game;
    }

    public MainPanel getMainPanel()
    {
        return mainPanel;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
    }
}
