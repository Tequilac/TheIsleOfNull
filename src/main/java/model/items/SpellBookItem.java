package model.items;

public class SpellBookItem extends Item
{
    private final String spellName;

    public SpellBookItem(int cost, String name, String type, String spellName)
    {
        super(cost, name, type);
        this.spellName = spellName;
    }

    public String getSpellName()
    {
        return spellName;
    }

    @Override
    public int getValue()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Contains spell: " + spellName;
    }
}
