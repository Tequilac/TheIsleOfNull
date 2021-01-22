import game.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        EventQueue.invokeLater(() ->
        {
            JFrame ex = null;
            try
            {
                ex = new Frame(new Game());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }
}
