package model.saves;

import model.entities.Enemy;
import model.entities.Entity;
import model.entities.Group;
import model.game.Game;
import model.map.*;
import model.map.districts.World;
import model.quests.Quest;
import model.quests.QuestGiver;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class WorldParser implements IDistrictParser
{
    @Override
    public void parseDistrict(Game game, File file) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String currentDistrict = br.readLine();
        StringBuilder currentDistrictInfo = new StringBuilder(currentDistrict + "\n");
        String line = br.readLine();

        World district = (World) MapParser.parseMap(currentDistrict);

        while (!line.equals(currentDistrict))
        {
            line = br.readLine();
        }
        String visited = br.readLine();
        currentDistrictInfo.append(visited);
        boolean[][] visibleTiles = new boolean[district.getWidth()][district.getHeight()];

        if(visited.equals("V"))
        {
            for (int i = 0; i < district.getHeight(); i++)
            {
                String mapLine = br.readLine();
                currentDistrictInfo.append("\n").append(mapLine);
                for (int j = 0; j < district.getWidth(); j++)
                {
                    visibleTiles[j][i] = mapLine.charAt(j) == '1';
                }
            }

            district.setVisibleTiles(visibleTiles);

            int enemiesNumber = Integer.parseInt(br.readLine());
            String name;
            String race;
            int amount;
            int level;
            int experience;
            int x;
            int y;
            MapDirection mapDirection;
            currentDistrictInfo.append("\n").append(enemiesNumber);
            LinkedList<Group> enemies = new LinkedList<>();
            if (enemiesNumber != 0)
            {
                for (int i = 0; i < enemiesNumber; i++)
                {
                    name = br.readLine();
                    currentDistrictInfo.append("\n").append(name);
                    race = br.readLine();
                    currentDistrictInfo.append("\n").append(race);
                    amount = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(amount);
                    level = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(level);
                    experience = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(experience);
                    x = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(x);
                    y = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(y);
                    mapDirection = MapDirection.valueOf(br.readLine());
                    currentDistrictInfo.append("\n").append(mapDirection);
                    ArrayList<Entity> entities = new ArrayList<>(amount);
                    int health;
                    for (int j = 0; j < amount; j++)
                    {
                        health = Integer.parseInt(br.readLine());
                        currentDistrictInfo.append("\n").append(health);
                        entities.add(new Enemy(name, health, game.getEnemyRace(race), level, experience));
                    }
                    enemies.add(new Group(new Vector2d(x, y), mapDirection, entities));
                }
            }
            district.setEnemies(enemies);

            if(district.getChests() != null)
            {
                int chestsNumber = Integer.parseInt(br.readLine());
                String itemName;
                int gold;
                int itemAmount;
                currentDistrictInfo.append("\n").append(chestsNumber);
                ArrayList<Chest> chests = district.getChests();
                for (int i = 0; i < chestsNumber; i++)
                {
                    Chest chest = chests.get(i);
                    gold = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(gold);
                    chest.setGold(gold);
                    itemAmount = Integer.parseInt(br.readLine());
                    currentDistrictInfo.append("\n").append(itemAmount);
                    LinkedList<String> items = new LinkedList<>();
                    for (int j = 0; j < itemAmount; j++)
                    {
                        itemName = br.readLine();
                        currentDistrictInfo.append("\n").append(itemName);
                        items.add(itemName);
                    }
                    chest.getItems().removeIf(item -> !items.contains(item.getName()));
                }
            }

            ArrayList<QuestGiver> questGivers = district.getQuestGivers();
            if (questGivers != null)
            {
                for (QuestGiver questGiver : questGivers)
                {
                    ArrayList<Quest> quests = questGiver.getQuests();
                    for(int i = 0; i < quests.size(); i++)
                    {
                        Quest newQuest = game.getTeam().findQuest(quests.get(i).getName());
                        if(newQuest != null)
                            quests.set(i, newQuest);
                    }
                    quests.removeIf(Quest::isCompleted);
                }
            }
        }


//        game.setCurrentDistrict(district);
//        game.getCurrentDistrict().setLoadedInfo(currentDistrictInfo.toString());
    }
}
