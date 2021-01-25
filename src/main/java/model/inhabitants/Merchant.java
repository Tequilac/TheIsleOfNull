package model.inhabitants;

import model.items.Item;
import model.map.Vector2d;

import java.util.ArrayList;

public class Merchant extends Inhabitant
{
    private ArrayList<Item> offeredItems;

    public Merchant(String name, String description, Vector2d position, ArrayList<Item> offeredItems)
    {
        super(name, description, position);
        this.offeredItems = offeredItems;
    }

    public ArrayList<Item> getOfferedItems()
    {
        return offeredItems;
    }
}
