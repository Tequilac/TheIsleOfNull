package entities;


import items.Item;
import map.MapDirection;
import map.Vector2d;
import quests.Quest;
import quests.QuestBringItem;
import quests.QuestKillEnemies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

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

    public Team(Vector2d position, MapDirection mapDirection, ArrayList<Character> teamMembers, LinkedList<Quest> quests, ArrayList<Item> items)
    {
        super(position, mapDirection);
        this.entities = teamMembers;
        this.gold = 200;
        this.activeQuests = new LinkedList<>();
        this.activeQuests = Objects.requireNonNullElseGet(quests, LinkedList::new);
        this.itemsInInventory = Objects.requireNonNullElseGet(items, ArrayList::new);
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

    public boolean addItemToInventory(Item item)
    {
        for(Item value : itemsInInventory)
        {
            if(item.getName().equals(value.getName()))
            {
                return false;
            }
        }
        itemsInInventory.add(item);
        updateItemQuests(item);
        return true;
    }

    public void removeItemFromInventory(Item item)
    {
        int toRemove = -1;
        for(int i = 0; i < itemsInInventory.size(); i++)
        {
            if(item.getName().equals(itemsInInventory.get(i).getName()))
            {
                toRemove = i;
                break;
            }
        }
        itemsInInventory.remove(toRemove);
    }

    public void addActiveQuest(Quest quest)
    {
        if(!activeQuests.contains(quest))
            activeQuests.add(quest);
    }

    public void updateKillQuests(Enemy enemy)
    {
        for(Quest quest : activeQuests)
        {
            if(quest instanceof QuestKillEnemies && ((QuestKillEnemies) quest).getTargetName().equals(enemy.getName()) && !quest.isCompleted())
                quest.updateQuest();
        }
    }

    public void updateItemQuests(Item item)
    {
        for(Quest quest : activeQuests)
        {
            if(quest instanceof QuestBringItem && ((QuestBringItem) quest).getRequiredItem().equals(item.getName()) && !quest.isCompleted())
                quest.updateQuest();
        }
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
