package entities;


import items.Item;
import map.MapDirection;
import map.Vector2d;
import quests.Quest;

import java.util.ArrayList;
import java.util.LinkedList;

public class Team extends Group
{
    private ArrayList<Character> entities;

    private int gold;

    private LinkedList<Quest> activeQuests;

    private ArrayList<Item> itemsInInventory;

    public Team(Vector2d position, MapDirection mapDirection, ArrayList<Character> teamMembers)
    {
        super(position, mapDirection);
        this.entities = teamMembers;
        this.gold = 200;
        this.activeQuests = new LinkedList<>();
        this.itemsInInventory = new ArrayList<>();
    }

    public Team(Vector2d position, MapDirection mapDirection, ArrayList<Character> teamMembers, ArrayList<Item> items)
    {
        super(position, mapDirection);
        this.entities = teamMembers;
        this.gold = 200;
        this.activeQuests = new LinkedList<>();
        if(items == null)
            this.itemsInInventory = new ArrayList<>();
        else
            this.itemsInInventory = items;
    }

    public void distributeExperience(int experience, int currentMember)
    {
        int experienceForKiller = 2 * experience / 5;
        int leftMembers = 3;
        int restOfExperience = experience - experienceForKiller;
        entities.get(currentMember).addExperience(experienceForKiller);
        for (int i = 0; i < 4; i++)
        {
            if(i != currentMember)
            {
                entities.get(i).addExperience(restOfExperience / leftMembers);
                restOfExperience -= restOfExperience / leftMembers;
                leftMembers--;
            }
        }
    }

    public void equipItem(Item item)
    {

    }

    public void addItemToInventory(Item item)
    {
        itemsInInventory.add(item);
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    public ArrayList<Character> getTeamMembers()
    {
        return entities;
    }

    public void setEntities(ArrayList<Character> entities)
    {
        this.entities = entities;
    }

    public int getGold()
    {
        return gold;
    }

    public LinkedList<Quest> getActiveQuests()
    {
        return activeQuests;
    }

    public ArrayList<Item> getItemsInInventory()
    {
        return itemsInInventory;
    }

    public void setGold(int gold)
    {
        this.gold = gold;
    }
}
