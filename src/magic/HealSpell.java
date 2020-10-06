package magic;

public class HealSpell extends Spell
{
    private int value;

    private boolean multiple;

    public HealSpell(MagicSchool magicSchool, String name, String description, int manaCost, int spellLevel, int value, boolean multiple)
    {
        super(magicSchool, name, description, manaCost, spellLevel);
        this.value = value;
        this.multiple = multiple;
    }
}
