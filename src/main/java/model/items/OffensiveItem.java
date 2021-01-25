package model.items;


public class OffensiveItem extends Item
{

    protected final int minDamage;

    protected final int maxDamage;

    public OffensiveItem(int cost, String name, String type, int minDamage, int maxDamage)
    {
        super(cost, name, type);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public int getValue()
    {
        return (int) (Math.random()*(maxDamage - minDamage + 1) + minDamage);
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Damage: " + minDamage + "-" + maxDamage;
    }
}
