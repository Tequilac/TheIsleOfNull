package model.map;

import model.items.Item;

import java.util.LinkedList;

public class Chest
{
    private final Vector2d position;

    private int gold;

    private LinkedList<Item> items;

    public Chest(Vector2d position, int gold, LinkedList<Item> items)
    {
        this.position = position;
        this.gold = gold;
        this.items = items;
    }

    public Vector2d getPosition()
    {
        return position;
    }

    public int getGold()
    {
        return gold;
    }

    public LinkedList<Item> getItems()
    {
        return items;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }
}
