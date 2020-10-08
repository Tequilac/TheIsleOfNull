package visuals.primaryPanels.teamPanels;

import entities.Character;
import entities.MagicCharacter;
import magic.Spell;
import main.Game;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.util.LinkedList;

public class KnownSpellsPanel extends PrimaryPanel
{
    public KnownSpellsPanel(Game game, Frame frame)
    {
        super(game, frame);

        drawSpells(game.getCurrentCharacter());
    }

    private void drawSpells(Character character)
    {
        if(character instanceof MagicCharacter)
        {
            LinkedList<Spell> spells = ((MagicCharacter) character).getSpells();
            JLabel[] spellLabels = new JLabel[spells.size()];
            for(int i = 0; i < spells.size(); i++)
            {
                spellLabels[i] = spells.get(i).getLabel();
                spellLabels[i].setBounds(0, 20 + i*50, 40, 40);
                add(spellLabels[i]);
            }
        }
        else
        {
            JLabel info = new JLabel("This character doesn't use magic.");
            info.setBounds(10, 10, 200, 20);
            add(info);
        }
    }
}
