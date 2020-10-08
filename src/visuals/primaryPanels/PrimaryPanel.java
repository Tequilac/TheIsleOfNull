package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class PrimaryPanel extends JPanel
{
    protected final Game game;

    protected final Frame frame;

    public PrimaryPanel(Game game, Frame frame)
    {
        this.game = game;
        this.frame = frame;

        setLayout(null);

        setPreferredSize(new Dimension(800, 800));
    }
}
