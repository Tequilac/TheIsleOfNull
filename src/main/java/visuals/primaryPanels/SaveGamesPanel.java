package visuals.primaryPanels;

import model.game.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class SaveGamesPanel extends PrimaryPanel
{
    public LinkedList<File> files = new LinkedList<>();

    public LinkedList<String> fileNames = new LinkedList<>();

    public JButton[] buttons;

    public JButton closeButton;

    public SaveGamesPanel(Game game, Frame frame)
    {
        super(game, frame);

        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        closeButton = new JButton("Exit to Main Menu");
        closeButton.addActionListener(actionEvent ->
                frame.openStartingPanel());
        closeButton.setBackground(Color.GRAY);
        closeButton.setAlignmentX(CENTER_ALIGNMENT);
        add(closeButton);

        File folder = new File("saveGames");
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles()))
        {
            files.add(fileEntry);
            fileNames.add(fileEntry.getName());
        }
        buttons = new JButton[fileNames.size()];
        for (int i = 0; i < fileNames.size(); i++)
        {
            buttons[i] = new JButton(fileNames.get(i));
            int finalI = i;
            buttons[i].addActionListener(actionEvent ->
            {
                //game.loadSaveGame(files.get(finalI));
                frame.openMapPanel();
            });
            buttons[i].setAlignmentX(CENTER_ALIGNMENT);
            add(buttons[i]);
        }
    }
}
