package model.quests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.items.Item;
import model.map.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QuestParser
{
    public static ArrayList<QuestGiver> parseQuests(String filename) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/questGivers/" + filename + "Quests.json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        int amount = jsonObject.get("amount").getAsInt();

        ArrayList<QuestGiver> questGivers = new ArrayList<>();
        int questsAmount;
        String name;
        int x;
        int y;
        String questName;

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
                questName = jsonArray.get(j).getAsString();
                quests.add(parseQuest(questName, QuestStatus.Available));
            }
            questGivers.add(new QuestGiver(name, quests, new Vector2d(x, y)));
        }
        return questGivers;
    }

    public static Quest parseQuest(String filename, QuestStatus status) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/quests/" + filename + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        String type;
        String description;
        int goldReward;
        int experienceReward;
        int itemsRewardAmount;
        String targetName;
        int requiredAmount;
        String requiredItem;

        type = jsonObject.get("type").getAsString();
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
        switch(type)
        {
            case "KillEnemies":
                targetName = jsonObject.get("targetName").getAsString();
                requiredAmount = jsonObject.get("requiredAmount").getAsInt();
                return new QuestKillEnemies(filename, description, goldReward, experienceReward, itemsReward, targetName, requiredAmount, status);
            case "BringItem":
                requiredItem = jsonObject.get("requiredItem").getAsString();
                requiredAmount = jsonObject.get("requiredAmount").getAsInt();
                return new QuestBringItem(filename, description, goldReward, experienceReward, itemsReward, requiredItem, requiredAmount, status);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
