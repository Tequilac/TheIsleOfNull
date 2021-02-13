package model.map.districts;

import model.inhabitants.Inhabitant;
import model.map.Chest;
import model.map.Vector2d;
import model.map.tiles.TileType;
import model.quests.QuestGiver;

import java.util.ArrayList;

public class Town extends District
{
    private final ArrayList<Inhabitant> inhabitants;

    public Town(String name, int width, int height, int[][] tiles, ArrayList<Chest> chests,
                ArrayList<QuestGiver> questGivers, ArrayList<Inhabitant> inhabitants)
    {
        super(name, width, height, tiles, chests, questGivers);
        this.inhabitants = inhabitants;
    }

    public TileType getTileType(Vector2d position)
    {
        return TileType.GRASS;
    }

    public boolean canMove(Vector2d position)
    {
        return true;
    }

    public ArrayList<Inhabitant> getInhabitants()
    {
        return inhabitants;
    }
}
