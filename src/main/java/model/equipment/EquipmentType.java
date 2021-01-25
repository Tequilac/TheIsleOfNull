package model.equipment;

import model.items.DefensiveItem;
import model.items.Item;
import model.items.OffensiveItem;

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
