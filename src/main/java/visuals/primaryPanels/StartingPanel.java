package visuals.primaryPanels;

import model.game.Game;
import visuals.Frame;

import javax.swing.*;

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
        JButton loadGameButton = new JButton("Load model.game.model.game.Game");
        loadGameButton.setAlignmentX(CENTER_ALIGNMENT);
        loadGameButton.addActionListener(actionEvent -> frame.openSaveGamesPanel());
        add(loadGameButton);
    }
}
