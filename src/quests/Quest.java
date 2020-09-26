package quests;

import entities.Team;
import items.Item;

import java.util.ArrayList;


public abstract class Quest
{
    protected String name;

    protected String description;

    protected int goldReward;

    protected int experienceReward;

    protected ArrayList<Item> itemsReward;

    protected QuestStatus questStatus;

    public Quest(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward)
    {
        this.name = name;
        this.description = description;
        this.goldReward = goldReward;
        this.experienceReward = experienceReward;
        this.itemsReward = itemsReward;
        this.questStatus = QuestStatus.Available;
    }

    public abstract void updateQuest();

    public abstract boolean isCompleted();

    public void transferRewards(Team team)
    {
        if(goldReward != 0)
        {
            team.addGold(goldReward);
        }
        if(experienceReward != 0)
        {
            team.distributeExperience(experienceReward, -1);
        }
        if(itemsReward != null)
        {
            for(Item item : itemsReward)
            {
                team.addItemToInventory(item);
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public QuestStatus getQuestStatus()
    {
        return questStatus;
    }

    public int getGoldReward()
    {
        return goldReward;
    }

    public int getExperienceReward()
    {
        return experienceReward;
    }

    public ArrayList<Item> getItemsReward()
    {
        return itemsReward;
    }

    public void setQuestStatus(QuestStatus questStatus)
    {
        this.questStatus = questStatus;
    }

    @Override
    public String toString()
    {
        return  name + "<br>" +
                description;
    }
}
