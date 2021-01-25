package model.magic;

public class DamageSpell extends Spell
{
    private int value;

    public DamageSpell(MagicSchool magicSchool, String name, String description, int manaCost, int spellLevel, int value)
    {
        super(magicSchool, name, description, manaCost, spellLevel);
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Value: " + value;
    }
}
