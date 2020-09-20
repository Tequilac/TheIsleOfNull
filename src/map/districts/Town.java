package map.districts;

import entities.Group;
import inhabitants.Inhabitant;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class Town extends District
{
    private ArrayList<Inhabitant> inhabitants;

    public Town(String name, int height, int width, int[][] tiles, LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers, ArrayList<Inhabitant> inhabitants)
    {
        super(name, height, width, tiles, enemies, chests, questGivers);
        this.inhabitants = inhabitants;
    }

    public boolean canMove(Vector2d position)
    {
        return true;
    }

    public boolean isExit(Vector2d position)
    {
        return position.equals(new Vector2d(0, 0));
    }
}
