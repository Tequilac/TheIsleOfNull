package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class PrimaryPanel extends JPanel
{
    protected Game game;

    protected Frame frame;

    public PrimaryPanel(Game game, Frame frame)
    {
        this.game = game;
        this.frame = frame;

        setPreferredSize(new Dimension(800, 800));
    }
}
