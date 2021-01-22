package map.districts;

import entities.Group;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class District
{
    protected String name;

    protected String loadedInfo;

    protected int height;

    protected int width;

    protected int[][] tiles;

    protected ArrayList<Chest> chests;

    protected ArrayList<QuestGiver> questGivers;

    public District(String name, int height, int width, int[][] tiles, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        this.name = name;
        this.height = height;
        this.width = width;
        this.tiles = tiles;
        this.chests = chests;
        this.questGivers = questGivers;
    }

    public abstract boolean canMove(Vector2d position);

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

    public ArrayList<Chest> getChests()
    {
        return chests;
    }

    public ArrayList<QuestGiver> getQuestGivers()
    {
        return questGivers;
    }
}
