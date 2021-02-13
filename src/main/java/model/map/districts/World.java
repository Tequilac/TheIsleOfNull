package model.map.districts;

import model.entities.Group;
import model.map.Chest;
import model.map.Vector2d;
import model.map.tiles.TileType;
import model.quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

import static model.map.tiles.TileType.*;

public class World extends AbstractHiddenDistrict
{
    private final Vector2d townEntrance;

    private final Vector2d dungeonEntrance;

    public World(String name, int width, int height, int[][] tiles, boolean[][] visibleTiles,
                 LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers,
                 Vector2d townEntrance, Vector2d dungeonEntrance)
    {
        super(name, width, height, tiles, visibleTiles, enemies, chests, questGivers);
        this.townEntrance = townEntrance;
        this.dungeonEntrance = dungeonEntrance;
    }

    public TileType getTileType(Vector2d position)
    {
        if(!position.inBounds(width, height))
            return null;
        return switch(tiles[position.getX()][position.getY()])
                {
                    case 0 -> WATER;
                    case 1 -> GRASS;
                    case 2 -> SAND;
                    default -> throw new IllegalStateException("Unexpected value: " + tiles[position.getX()][position.getY()]);
                };
    }

    public boolean isTownEntrance(Vector2d position)
    {
        if(townEntrance == null)
            return false;
        return townEntrance.equals(position);
    }

    public boolean isDungeonEntrance(Vector2d position)
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
