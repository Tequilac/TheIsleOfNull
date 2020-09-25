package quests;

import items.Item;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuestKillEnemies extends Quest
{
    private final String targetName;

    private final int requiredAmount;

    private int currentAmount;

    public QuestKillEnemies(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, String targetName, int requiredAmount, int currentAmount)
    {
        super(name, description, goldReward, experienceReward, itemsReward);
        this.targetName = targetName;
        this.requiredAmount = requiredAmount;
        this.currentAmount = currentAmount;
    }

    public QuestKillEnemies(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, String targetName, int requiredAmount)
    {
        super(name, description, goldReward, experienceReward, itemsReward);
        this.targetName = targetName;
        this.requiredAmount = requiredAmount;
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

    public String getTargetName()
    {
        return targetName;
    }

    @Override
    public String toString()
    {
        return super.toString() + "<br>" +
                "Target: " + targetName + "<br>" +
                "Required amount: " + requiredAmount + "<br>" +
                "Current amount: " + currentAmount;
    }
}
