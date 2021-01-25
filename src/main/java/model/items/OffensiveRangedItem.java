package model.items;

public class OffensiveRangedItem extends OffensiveItem
{
    protected final int range;

    public OffensiveRangedItem(int cost, String name, String type, int minDamage, int maxDamage, int range)
    {
        super(cost, name, type, minDamage, maxDamage);
        this.range = range;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Range: " + range;
    }
}
