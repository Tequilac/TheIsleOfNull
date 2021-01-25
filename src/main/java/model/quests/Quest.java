package model.quests;

import model.entities.Team;
import model.items.Item;

import java.util.ArrayList;


public abstract class Quest
{
    protected final String name;

    protected final String description;

    protected final int goldReward;

    protected final int experienceReward;

    protected final ArrayList<Item> itemsReward;

    protected QuestStatus questStatus;

    protected final int requiredAmount;

    protected int currentAmount;

    public Quest(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, QuestStatus status, int requiredAmount)
    {
        this.name = name;
        this.description = description;
        this.goldReward = goldReward;
        this.experienceReward = experienceReward;
        this.itemsReward = itemsReward;
        this.questStatus = status;
        this.requiredAmount = requiredAmount;
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

    public int getCurrentAmount()
    {
        return currentAmount;
    }

    public void setQuestStatus(QuestStatus questStatus)
    {
        this.questStatus = questStatus;
    }

    public void setCurrentAmount(int currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    @Override
    public String toString()
    {
        return  name + "<br>" +
                description;
    }
}
