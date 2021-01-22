package items;

public class DefensiveItem extends Item
{
    protected final int damageReduction;

    public DefensiveItem(int cost, String name, String type, int damageReduction)
    {
        super(cost, name, type);
        this.damageReduction = damageReduction;
    }

    @Override
    public int getValue()
    {
        return damageReduction;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Damage reduction: " + damageReduction;
    }
}
