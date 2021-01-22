package map;

import com.google.gson.*;
import entities.Enemy;
import entities.Entity;
import entities.Group;
import entities.MonsterParser;
import inhabitants.Inhabitant;
import inhabitants.MerchantParser;
import inhabitants.TrainerParser;
import items.*;
import map.districts.District;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.QuestGiver;
import quests.QuestParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MapParser
{
    public static District parseMap(String filename) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/maps/" + filename + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int height = jsonObject.get("height").getAsInt();
        int width = jsonObject.get("width").getAsInt();
        int monstersCount = jsonObject.get("monstersCount").getAsInt();
        Vector2d town = null;
        Vector2d dungeon = null;
        if(filename.charAt(0) == 'W' && jsonObject.get("hasTownEntrance").getAsBoolean())
        {
            town = new Vector2d(jsonObject.get("townEntrance").getAsJsonObject().get("x").getAsInt(), jsonObject.get("townEntrance").getAsJsonObject().get("y").getAsInt());
        }
        if(filename.charAt(0) == 'W' && jsonObject.get("hasDungeonEntrance").getAsBoolean())
        {
            dungeon = new Vector2d(jsonObject.get("dungeonEntrance").getAsJsonObject().get("x").getAsInt(), jsonObject.get("dungeonEntrance").getAsJsonObject().get("y").getAsInt());
        }


        int[][] tiles = new int[width][height];


        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/maps/" + filename + ".txt"));

        String st;

        for (int i = 0; i < width; i++)
        {
            st = br.readLine();
            for (int j = 0; j < height; j++)
            {
                tiles[i][j] = Character.getNumericValue(st.charAt(j));
            }
        }

        JsonArray jsonArray = jsonObject.get("monsters").getAsJsonArray();

        LinkedList<Group> enemyGroups = new LinkedList<>();
        String name;
        int amount;
        int level;
        int experience;
        int x;
        int y;

        for (int i = 0; i < monstersCount; i++)
        {
            jsonObject = jsonArray.get(i).getAsJsonObject();
            name = jsonObject.get("name").getAsString();
            amount = jsonObject.get("amount").getAsInt();
            level = jsonObject.get("level").getAsInt();
            experience = jsonObject.get("experience").getAsInt();
            ArrayList<Entity> enemies = new ArrayList<>(amount);
            for (int j = 0; j < amount; j++)
            {
                Enemy enemy = MonsterParser.read(name, level, experience);
                enemies.add(enemy);
            }
            x = jsonObject.get("x").getAsInt();
            y = jsonObject.get("y").getAsInt();
            enemyGroups.add(new Group(new Vector2d(x, y), MapDirection.North, enemies));
        }

        jsonObject = jsonElement.getAsJsonObject();

        ArrayList<QuestGiver> questGivers = null;
        if(filename.charAt(0) != 'D')
        {
            if(jsonObject.get("hasQuestGivers").getAsBoolean())
                questGivers = QuestParser.parseQuests(filename);
        }

        ArrayList<Chest> chests = null;
        if(jsonObject.get("hasChests").getAsBoolean())
                chests = ChestParser.parseChests(filename);

        if(filename.charAt(0) != 'T')
        {
            boolean[][] visibleTiles = new boolean[width][height];
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    visibleTiles[j][i] = false;
                }
            }
            if(filename.charAt(0) == 'W')
                return new World(filename, height, width, tiles, visibleTiles, enemyGroups, chests, questGivers, town, dungeon);
            return new Dungeon(filename, height, width, tiles, visibleTiles, enemyGroups, chests, questGivers);
        }

        int merchantsAmount = jsonObject.get("merchantsAmount").getAsInt();
        int trainersAmount = jsonObject.get("trainersAmount").getAsInt();
        ArrayList<Inhabitant> inhabitants = new ArrayList<>(merchantsAmount + trainersAmount);
        jsonArray = jsonObject.get("merchants").getAsJsonArray();
        for (int i = 0; i < merchantsAmount; i++)
        {
            inhabitants.add(MerchantParser.parseMerchant(jsonArray.get(i).getAsString()));
        }
        jsonArray = jsonObject.get("trainers").getAsJsonArray();
        for (int i = 0; i < trainersAmount; i++)
        {
            inhabitants.add(TrainerParser.parseTrainer(jsonArray.get(i).getAsString()));
        }
        return new Town(filename, height, width, tiles, chests, questGivers, inhabitants);
    }
}
