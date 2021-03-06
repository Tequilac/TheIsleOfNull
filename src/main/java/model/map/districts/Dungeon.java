package model.map.districts;

import model.entities.Group;
import model.map.Chest;
import model.map.Vector2d;
import model.map.tiles.TileType;
import model.quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class Dungeon extends AbstractHiddenDistrict
{
    public Dungeon(String name, int width, int height, int[][] tiles, boolean[][] visibleTiles,
                   LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        super(name, width, height, tiles, visibleTiles, enemies, chests, questGivers);
    }

    public TileType getTileType(Vector2d position)
    {
        return TileType.GRASS;
    }
}
