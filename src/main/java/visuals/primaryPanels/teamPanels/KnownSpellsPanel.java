package visuals.primaryPanels.teamPanels;

import entities.Character;
import entities.MagicCharacter;
import game.Game;
import magic.Spell;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.util.LinkedList;

public class KnownSpellsPanel extends PrimaryPanel
{
    private JLabel[] spellLabels;

    private JLabel info;

    public KnownSpellsPanel(Game game, Frame frame)
    {
        super(game, frame);

        drawSpells(game.getCurrentCharacter());
    }

    public void drawSpells(Character character)
    {
        if(spellLabels != null)
        {
            for(JLabel label : spellLabels)
                remove(label);
        }
        if(info != null)
        {
            remove(info);
        }
        if(character instanceof MagicCharacter)
        {
            LinkedList<Spell> spells = ((MagicCharacter) character).getSpells();
            spellLabels = new JLabel[spells.size()];
            for(int i = 0; i < spells.size(); i++)
            {
                spellLabels[i] = spells.get(i).getLabel();
                spellLabels[i].setBounds(10, 20 + i*50, 40, 40);
                add(spellLabels[i]);
            }
        }
        else
        {
            info = new JLabel("This character doesn't use magic.");
            info.setBounds(10, 10, 200, 20);
            add(info);
        }
    }
}
