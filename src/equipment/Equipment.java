package equipment;

import items.Item;

public class Equipment
{
    private Item item;

    public Equipment(Item item)
    {
        this.item = item;
    }

    public int getValue()
    {
        return item.getValue();
    }

    public Item getItem()
    {
        return item;
    }
}
