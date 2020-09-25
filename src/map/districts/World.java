package map.districts;

import entities.Group;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class World extends District
{
    private boolean[][] visibleTiles;

    private Vector2d townEntrance;

    private Vector2d dungeonEntrance;

    public World(String name, int height, int width, int[][] tiles, boolean[][] visibleTiles, LinkedList<Group> enemies,
                 ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers, Vector2d townEntrance, Vector2d dungeonEntrance)
    {
        super(name, height, width, tiles, enemies, chests, questGivers);
        this.visibleTiles = visibleTiles;
        this.townEntrance = townEntrance;
        this.dungeonEntrance = dungeonEntrance;
    }

    public boolean canMove(Vector2d position)
    {
        if(position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height)
            return true;
        return tiles[position.getY()][position.getX()] != 0;
    }

    public boolean isVisible(Vector2d position)
    {
        return (visibleTiles[position.getY()][position.getX()]);
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

    public void setVisibleTiles(boolean[][] visibleTiles)
    {
        this.visibleTiles = visibleTiles;
    }

    public boolean[][] getVisibleTiles()
    {
        return visibleTiles;
    }

    public Vector2d getTownEntrance()
    {
        return townEntrance;
    }

    public Vector2d getDungeonEntrance()
    {
        return dungeonEntrance;
    }

    public void updateVisibleTiles(Vector2d position)
    {
        int posX = position.getX();
        int posY = position.getY();
        for (int i = -4; i <= 4; i++)
        {
            for (int j = -4; j <= 4; j++)
            {
                if(posY + i > -1 && posY + i < height && posX + j > -1 && posX + j < width)
                    visibleTiles[posY + i][posX + j] = true;
            }
        }
    }
}
