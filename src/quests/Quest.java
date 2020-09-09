package quests;

import items.Item;

import java.util.ArrayList;


public class Quest
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
}
