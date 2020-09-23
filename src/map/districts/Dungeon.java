package map.districts;

import entities.Group;
import map.Chest;
import map.Vector2d;
import quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class Dungeon extends District
{
    private boolean[][] visibleTiles;

    public Dungeon(String name, int height, int width, int[][] tiles, boolean[][] visibleTiles, LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        super(name, height, width, tiles, enemies, chests, questGivers);
        this.visibleTiles = visibleTiles;
    }

    public boolean canMove(Vector2d position)
    {
        if(position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height)
            return true;
        return tiles[position.getY()][position.getX()] != 0;
    }

    public void setVisibleTiles(boolean[][] visibleTiles)
    {
        this.visibleTiles = visibleTiles;
    }

    public boolean[][] getVisibleTiles()
    {
        return visibleTiles;
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
