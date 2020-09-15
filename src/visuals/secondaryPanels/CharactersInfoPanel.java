package visuals.secondaryPanels;

import entities.Character;
import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class CharactersInfoPanel extends SecondaryPanel
{
    private CharacterInfoPanel[] characterInfoPanels;

    public CharactersInfoPanel(Game game, Frame frame)
    {
        super(game, frame);
    }

    public void drawCharactersInfo()
    {
        if(characterInfoPanels == null)
        {
            characterInfoPanels = new CharacterInfoPanel[4];

            for (int i = 0; i < 4; i++)
            {
                characterInfoPanels[i] = new CharacterInfoPanel(game, frame, game.getCharacters().get(i));
                characterInfoPanels[i].setBounds(0,200*i, 300,200);
                add(characterInfoPanels[i]);
            }
        }

        for(int i = 0; i < 4; i++)
        {
            characterInfoPanels[i].repaint();
        }

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawCharactersInfo();
    }
}
