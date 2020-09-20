package equipment;

import items.Item;

public class Equipment
{
    private final EquipmentType type;

    private Item item;

    public Equipment(EquipmentType type)
    {
        this.type = type;
    }

    public boolean addItem(Item item)
    {
        if(this.item != null && type.compatibleWith(item))
        {
            this.item = item;
            return true;
        }
        return false;
    }

    public void clear()
    {
        item = null;
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
