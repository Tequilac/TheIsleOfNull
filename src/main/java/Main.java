import com.google.inject.Guice;
import com.google.inject.Injector;
import game.Game;
import guice.GameModule;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new GameModule());
        Game game = injector.getInstance(Game.class);
        EventQueue.invokeLater(() ->
        {
            JFrame ex = new Frame(game);
            ex.setVisible(true);
        });
    }
}
