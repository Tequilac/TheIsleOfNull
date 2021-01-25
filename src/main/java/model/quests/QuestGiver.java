package model.quests;

import model.map.Vector2d;

import java.util.ArrayList;

public class QuestGiver
{
    private String name;

    private ArrayList<Quest> quests;

    private Vector2d position;

    public QuestGiver(String name, ArrayList<Quest> quests, Vector2d position)
    {
        this.name = name;
        this.quests = quests;
        this.position = position;
    }

    public void removeQuest(int index)
    {
        quests.remove(index);
    }

    public ArrayList<Quest> getQuests()
    {
        return quests;
    }

    public String getName()
    {
        return name;
    }

    public Vector2d getPosition()
    {
        return position;
    }
}
