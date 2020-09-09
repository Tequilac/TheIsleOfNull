package quests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Enemy;
import entities.Entity;
import entities.Group;
import entities.MonsterParser;
import items.DefensiveItem;
import items.Item;
import items.OffensiveItem;
import items.OffensiveRangedItem;
import map.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class QuestParser
{
    public static ArrayList<QuestGiver> parseQuests(String filename) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/quests/" + filename + "Quests.json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        int amount = jsonObject.get("amount").getAsInt();

        ArrayList<QuestGiver> questGivers = new ArrayList<>();
        int gold;
        int questsAmount;
        String name;
        String type;
        String description;
        int goldReward;
        int experienceReward;
        int itemsRewardAmount;
        String targetName;
        int requiredAmount;
        String requiredItem;
        int x;
        int y;
        Quest quest;

        for (int i = 0; i < amount; i++)
        {
            jsonObject = jsonElement.getAsJsonObject();
            jsonArray = jsonObject.get("questGivers").getAsJsonArray();
            jsonObject = jsonArray.get(i).getAsJsonObject();
            name = jsonObject.get("name").getAsString();
            x = jsonObject.get("x").getAsInt();
            y = jsonObject.get("y").getAsInt();
            questsAmount = jsonObject.get("questsAmount").getAsInt();
            jsonArray = jsonObject.get("quests").getAsJsonArray();
            ArrayList<Quest> quests = new ArrayList<>();
            for (int j = 0; j < questsAmount; j++)
            {
                jsonObject = jsonArray.get(j).getAsJsonObject();
                type = jsonObject.get("type").getAsString();
                name = jsonObject.get("name").getAsString();
                description = jsonObject.get("description").getAsString();
                goldReward = jsonObject.get("goldReward").getAsInt();
                experienceReward = jsonObject.get("experienceReward").getAsInt();
                itemsRewardAmount = jsonObject.get("itemsRewardAmount").getAsInt();
                jsonArray = jsonObject.get("itemsReward").getAsJsonArray();
                ArrayList<Item> itemsReward = new ArrayList<>();
                for (int k = 0; k < itemsRewardAmount; k++)
                {
                    jsonObject = jsonArray.get(k).getAsJsonObject();
                }
                switch (type)
                {
                    case "KillEnemies":
                        targetName = jsonObject.get("targetName").getAsString();
                        requiredAmount = jsonObject.get("requiredAmount").getAsInt();
                        quest = new QuestKillEnemies(name, description, goldReward, experienceReward, itemsReward, targetName, requiredAmount, 0);
                        quests.add(quest);
                        break;
                    case "BringItem":
                        requiredItem = jsonObject.get("requiredItem").getAsString();
                        requiredAmount = jsonObject.get("requiredAmount").getAsInt();
                        quest = new QuestBringItem(name, description, goldReward, experienceReward, itemsReward, requiredItem, requiredAmount, 0);
                        quests.add(quest);
                        break;
                }
            }
            questGivers.add(new QuestGiver(name, quests, new Vector2d(x, y)));
        }
        return questGivers;
    }
}
