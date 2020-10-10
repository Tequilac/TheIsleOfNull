package magic;

public class EffectSpell extends Spell
{
    private Effect effect;

    private int duration;

    public EffectSpell(MagicSchool magicSchool, String name, String description, int manaCost, int spellLevel)
    {
        super(magicSchool, name, description, manaCost, spellLevel);
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Effect: " + effect.getName() + "<br>" +
                "Duration:" + duration;
    }
}
