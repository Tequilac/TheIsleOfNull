package quests;

import items.Item;

import java.util.ArrayList;

public class QuestBringItem extends Quest
{
    private String requiredItem;

    private int requiredAmount;

    private int currentAmount;

    public QuestBringItem(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, String targetName, int requiredAmount, int currentAmount)
    {
        super(name, description, goldReward, experienceReward, itemsReward);
        this.requiredItem = targetName;
        this.requiredAmount = requiredAmount;
        this.currentAmount = currentAmount;
    }

    public QuestBringItem(String name, String description, int goldReward, int experienceReward, ArrayList<Item> itemsReward, String targetName, int requiredAmount)
    {
        super(name, description, goldReward, experienceReward, itemsReward);
        this.requiredItem = targetName;
        this.requiredAmount = requiredAmount;
        this.currentAmount = 0;
    }
}
