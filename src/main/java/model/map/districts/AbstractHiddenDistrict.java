package model.map.districts;

import model.entities.Group;
import model.map.Chest;
import model.map.Vector2d;
import model.quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbstractHiddenDistrict extends District
{
    protected boolean[][] visibleTiles;

    protected LinkedList<Group> enemies;

    public AbstractHiddenDistrict(String name, int height, int width, int[][] tiles, boolean[][] visibleTiles, LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        super(name, height, width, tiles, chests, questGivers);
        this.visibleTiles = visibleTiles;
        this.enemies = enemies;
    }

    public boolean canMove(Vector2d position)
    {
        if(position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height)
            return true;
        return tiles[position.getY()][position.getX()] != 0;
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

    public boolean isVisible(Vector2d position)
    {
        return (visibleTiles[position.getY()][position.getX()]);
    }

    public void setVisibleTiles(boolean[][] visibleTiles)
    {
        this.visibleTiles = visibleTiles;
    }

    public boolean[][] getVisibleTiles()
    {
        return visibleTiles;
    }

    public LinkedList<Group> getEnemies()
    {
        return enemies;
    }

    public void setEnemies(LinkedList<Group> enemies)
    {
        this.enemies = enemies;
    }
}
