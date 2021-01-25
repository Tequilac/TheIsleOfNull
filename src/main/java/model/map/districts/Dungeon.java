package model.map.districts;

import model.entities.Group;
import model.map.Chest;
import model.quests.QuestGiver;

import java.util.ArrayList;
import java.util.LinkedList;

public class Dungeon extends AbstractHiddenDistrict
{
    public Dungeon(String name, int height, int width, int[][] tiles, boolean[][] visibleTiles, LinkedList<Group> enemies, ArrayList<Chest> chests, ArrayList<QuestGiver> questGivers)
    {
        super(name, height, width, tiles, visibleTiles, enemies, chests, questGivers);
    }
}
