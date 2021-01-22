package map.districts;

import entities.Group;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class World extends AbstractHiddenDistrict
{
    private final Vector2d townEntrance;

    private final Vector2d dungeonEntrance;

    public World(String name, int height, int width, int[][] tiles, boolean[][] visibleTiles, LinkedList<Group> enemies,
                 ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers, Vector2d townEntrance, Vector2d dungeonEntrance)
    {
        super(name, height, width, tiles, visibleTiles, enemies, chests, questGivers);
        this.townEntrance = townEntrance;
        this.dungeonEntrance = dungeonEntrance;
    }

    public boolean isTown(Vector2d position)
    {
        if(townEntrance == null)
            return false;
        return townEntrance.equals(position);
    }

    public boolean isDungeon(Vector2d position)
    {
        if(dungeonEntrance == null)
            return false;
        return dungeonEntrance.equals(position);
    }

    public Vector2d getTownEntrance()
    {
        return townEntrance;
    }

    public Vector2d getDungeonEntrance()
    {
        return dungeonEntrance;
    }
}
