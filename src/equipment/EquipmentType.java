package equipment;

import items.DefensiveItem;
import items.Item;
import items.OffensiveItem;

public enum EquipmentType
{
    LeftHand, RightHand, Head, Torso;

    public boolean compatibleWith(Item item)
    {
        switch(this)
        {
            case LeftHand:
                return item instanceof DefensiveItem;
            case RightHand:
                return item instanceof OffensiveItem;
            case Head:
                return item.getType().equals("Helmet");
            case Torso:
                return item.getType().equals("Armor");
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
