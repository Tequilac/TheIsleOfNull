package magic;

import javax.swing.*;

public class Spell
{
    protected MagicSchool magicSchool;

    protected String name;

    protected String description;

    protected int manaCost;

    protected int spellLevel;

    public Spell(MagicSchool magicSchool, String name, String description, int manaCost, int spellLevel)
    {
        this.magicSchool = magicSchool;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.spellLevel = spellLevel;
    }

    public MagicSchool getMagicSchool()
    {
        return magicSchool;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getManaCost()
    {
        return manaCost;
    }

    public int getSpellLevel()
    {
        return spellLevel;
    }

    public JLabel getLabel()
    {
        String path = "res/graphics/spells/" + magicSchool.toString().toLowerCase() + ".png";
        JLabel label = new JLabel(new ImageIcon(path));
        label.setToolTipText("<html>" + toString() + "</html>");
        return label;
    }

    @Override
    public String toString()
    {
        return  name + "<br>" +
                description + "<br>" +
                "Magic school: " + magicSchool + "<br>" +
                "Mana cost: " + manaCost + "<br>" +
                "Level: " + spellLevel;
    }
}
