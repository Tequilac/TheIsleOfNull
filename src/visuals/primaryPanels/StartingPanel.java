package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class StartingPanel extends PrimaryPanel
{
    public StartingPanel(Game game, Frame frame)
    {
        super(game, frame);

        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(CENTER_ALIGNMENT);
        newGameButton.addActionListener(actionEvent -> frame.openCharacterCreationPanel());
        add(newGameButton);
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setAlignmentX(CENTER_ALIGNMENT);
        loadGameButton.addActionListener(actionEvent -> frame.openSaveGamesPanel());
        add(loadGameButton);
    }
}
