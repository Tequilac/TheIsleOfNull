package quests;

import items.Item;

import java.util.ArrayList;

public class QuestBringItem extends Quest
{
    private final String requiredItem;

    public QuestBringItem(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, String targetName, int requiredAmount, QuestStatus status)
    {
        super(name, description, goldReward, experienceReward, itemsReward, status, requiredAmount);
        this.requiredItem = targetName;
        this.currentAmount = 0;
    }

    @Override
    public void updateQuest()
    {
        currentAmount++;
    }

    @Override
    public boolean isCompleted()
    {
        return currentAmount == requiredAmount;
    }

    public String getRequiredItem()
    {
        return requiredItem;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Required item: " + requiredItem + "<br>" +
                "Required amount: " + requiredAmount + "<br>" +
                "Current amount: " + currentAmount;
    }
}
