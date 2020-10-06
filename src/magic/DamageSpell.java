package magic;

public class DamageSpell extends Spell
{
    private int value;

    public DamageSpell(MagicSchool magicSchool, String name, String description, int manaCost, int spellLevel, int value)
    {
        super(magicSchool, name, description, manaCost, spellLevel);
        this.value = value;
    }
}
