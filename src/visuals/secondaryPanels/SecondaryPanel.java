package visuals.secondaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class SecondaryPanel extends JPanel
{
    protected final Game game;

    protected final visuals.Frame frame;

    public SecondaryPanel(Game game, Frame frame)
    {
        this.game = game;
        this.frame = frame;

        setPreferredSize(new Dimension(300, 800));
        setLayout(null);
    }
}
