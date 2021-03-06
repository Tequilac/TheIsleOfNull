package model.entities;


import model.items.Item;
import model.map.MapDirection;
import model.map.Vector2d;
import model.quests.Quest;
import model.quests.QuestBringItem;
import model.quests.QuestKillEnemies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Team extends Group
{
    private ArrayList<Character> entities;

    private int gold;

    private LinkedList<Quest> activeQuests;

    private final LinkedList<Quest> completedQuests;

    private final ArrayList<Item> itemsInInventory;

    private String teamInfo;

    public Team(Vector2d position, MapDirection mapDirection, ArrayList<Character> teamMembers)
    {
        super(position, mapDirection);
        this.entities = teamMembers;
        this.gold = 200;
        this.activeQuests = new LinkedList<>();
        this.completedQuests = new LinkedList<>();
        this.itemsInInventory = new ArrayList<>();
    }

    public Team(Vector2d position, MapDirection mapDirection, ArrayList<Character> teamMembers, LinkedList<Quest> activeQuests, LinkedList<Quest> completedQuests, ArrayList<Item> items)
    {
        super(position, mapDirection);
        this.entities = teamMembers;
        this.gold = 200;
        this.activeQuests = new LinkedList<>();
        this.activeQuests = Objects.requireNonNullElseGet(activeQuests, LinkedList::new);
        this.completedQuests = Objects.requireNonNullElseGet(completedQuests, LinkedList::new);
        this.itemsInInventory = Objects.requireNonNullElseGet(items, ArrayList::new);
    }

    public void distributeExperience(int experience, int currentMember)
    {
        if(currentMember == -1)
        {
            int leftMembers = 4;
            int restOfExperience = experience;
            for (int i = 0; i < 4; i++)
            {
                entities.get(i).addExperience(restOfExperience / leftMembers);
                restOfExperience -= restOfExperience / leftMembers;
                leftMembers--;
            }
            return;
        }
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

    public void completeQuest(Quest quest)
    {
        int toRemove = -1;
        for(int i = 0; i < activeQuests.size(); i++)
        {
            if(quest.getName().equals(activeQuests.get(i).getName()))
            {
                toRemove = i;
                break;
            }
        }
        activeQuests.remove(toRemove);
        completedQuests.add(quest);
    }

    public Quest findQuest(String name)
    {
        for(Quest quest : activeQuests)
        {
            if(quest.getName().equals(name))
                return quest;
        }
        for(Quest quest : completedQuests)
        {
            if(quest.getName().equals(name))
                return quest;
        }
        return null;
    }

    public void addGold(int gold)
    {
        this.gold += gold;
    }

    public void removeGold(int gold)
    {
        this.gold -= gold;
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

    public LinkedList<Quest> getCompletedQuests()
    {
        return completedQuests;
    }

    public ArrayList<Item> getItemsInInventory()
    {
        return itemsInInventory;
    }

    public String getTeamInfo()
    {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo)
    {
        this.teamInfo = teamInfo;
    }
}
