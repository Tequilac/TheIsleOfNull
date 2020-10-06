package magic;

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
}
