package map.districts;

import entities.Group;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class District
{
    protected String name;

    protected String loadedInfo;

    protected int height;

    protected int width;

    protected int[][] tiles;

    protected LinkedList<Group> enemies;

    protected ArrayList<Chest> chests;

    protected ArrayList<QuestGiver> questGivers;

    public District(String name, int height, int width, int[][] tiles, LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        this.name = name;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.enemies = enemies;
        this.chests = chests;
        this.questGivers = questGivers;
    }

    public void monstersMakeDecisions(Vector2d teamPosition)
    {
        for (Group group : enemies)
        {
            group.makeDecision(teamPosition, this);
        }
    }

    public Group findEnemies(Vector2d position)
    {
        for(Group group : enemies)
        {
            if(group.getPosition().equals(position))
                return group;
        }
        return null;
    }

    public void removeGroup(Group group)
    {
        enemies.remove(group);
    }

    public void setLoadedInfo(String loadedInfo)
    {
        this.loadedInfo = loadedInfo;
    }

    public String getLoadedInfo()
    {
        return loadedInfo;
    }

    public String getName()
    {
        return name;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public int[][] getTiles()
    {
        return tiles;
    }

    public LinkedList<Group> getEnemies()
    {
        return enemies;
    }

    public ArrayList<Chest> getChests()
    {
        return chests;
    }

    public ArrayList<QuestGiver> getQuestGivers()
    {
        return questGivers;
    }

    public void setEnemies(LinkedList<Group> enemies)
    {
        this.enemies = enemies;
    }
}
