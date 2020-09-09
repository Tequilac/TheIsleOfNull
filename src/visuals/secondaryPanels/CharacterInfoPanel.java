package visuals.secondaryPanels;

import entities.Character;
import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class CharacterInfoPanel extends SecondaryPanel
{

    private JLabel[] names;

    private JLabel[] charactersHealthStats;

    private JLabel[] charactersManaStats;

    public CharacterInfoPanel(Game game, Frame frame)
    {
        super(game, frame);
    }

    public void drawCharacterInfo(Graphics g)
    {
        if(names == null)
        {
            names = new JLabel[4];


            charactersHealthStats = new JLabel[4];
            charactersManaStats = new JLabel[4];

            for (int i = 0; i < 4; i++)
            {
                names[i] = new JLabel(game.getCharacters().get(i).getName());
                names[i].setBounds(10,150*i + 20, 120,20);
                add(names[i]);


                charactersHealthStats[i] = new JLabel();
                charactersHealthStats[i].setBounds(140,150*i + 45,100,20);
                add(charactersHealthStats[i]);

                charactersManaStats[i] = new JLabel();
                charactersManaStats[i].setBounds(140,150*i + 70,100,20);
                add(charactersManaStats[i]);
            }
        }
        Character character;
        for (int i = 0; i < 4; i++)
        {
            character = game.getCharacters().get(i);
            g.setColor(new Color(0, 255, 0));
            g.drawRect(10,150*i + 45,280,20);
            g.fillRect(10,150*i + 45,280*character.getHealth()/character.getMaxHealth(),20);

            g.setColor(new Color(0, 0, 255));
            g.drawRect(10,150*i + 70,280,20);
            g.fillRect(10,150*i + 70,280*character.getMana()/character.getMaxMana(),20);

            charactersHealthStats[i].setText(character.getHealth() + "/" + character.getMaxHealth());

            charactersManaStats[i].setText(character.getMana() + "/" + character.getMaxMana());
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawCharacterInfo(g);
    }
}
