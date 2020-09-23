package visuals;

import main.Game;
import map.Chest;
import map.MoveDirection;
import quests.QuestGiver;
import visuals.primaryPanels.*;
import visuals.secondaryPanels.BlankPanel;
import visuals.secondaryPanels.CharacterInfoPanel;
import visuals.secondaryPanels.CharactersInfoPanel;
import visuals.secondaryPanels.OptionsPanel;

import javax.swing.*;
import java.awt.*;
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

        game.setFrame(this);
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
        game.createTeam();
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
        mainPanel.addPrimaryPanel(new ChestPanel(game, game.getOpenedChest(), this));
        pack();
    }

    public void meetQuestGiver()
    {
        mainPanel.addPrimaryPanel(new QuestsPanel(game, game.getMetQuestGiver(), this));
        pack();
    }

    public void toggleTeamView()
    {
        if(mainPanel.getPrimaryPanel() instanceof TeamPanel)
            openMapPanel();
        else
            openTeamView();
    }

    public void openTeamView()
    {
        mainPanel.addPrimaryPanel(new TeamPanel(game, this));
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
                    chosenDirection = MoveDirection.Front;
                    break;
                case KeyEvent.VK_A:
                    chosenDirection = MoveDirection.Left;
                    break;
                case KeyEvent.VK_D:
                    chosenDirection = MoveDirection.Right;
                    break;
                case KeyEvent.VK_S:
                    chosenDirection = MoveDirection.Back;
                    break;
                case KeyEvent.VK_Q:
                    chosenDirection = MoveDirection.TurnLeft;
                    break;
                case KeyEvent.VK_E:
                    chosenDirection = MoveDirection.TurnRight;
                    break;
            }
            if(chosenDirection != null)
            {
                try
                {
                    game.tick(chosenDirection);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
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
