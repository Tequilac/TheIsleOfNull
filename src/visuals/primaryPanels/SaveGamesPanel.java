package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class SaveGamesPanel extends PrimaryPanel
{
    public LinkedList<File> files = new LinkedList<>();

    public LinkedList<String> fileNames = new LinkedList<>();

    public JButton[] buttons;

    public SaveGamesPanel(Game game, Frame frame)
    {
        super(game, frame);

        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        File folder = new File("saveGames");
        for (final File fileEntry : folder.listFiles())
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
                try
                {
                    game.loadSaveGame(files.get(finalI));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                frame.openMapPanel();
            });
            buttons[i].setAlignmentX(CENTER_ALIGNMENT);
            add(buttons[i]);
        }
    }
}
