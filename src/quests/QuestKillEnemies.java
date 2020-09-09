package quests;

import items.Item;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuestKillEnemies extends Quest
{
    private String targetName;

    private int requiredAmount;

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
}
