package map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import items.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChestParser
{
    public static ArrayList<Chest> parseChests(String filename) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/chests/" + filename + "Chests.json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        int chestsCount = jsonObject.get("chestsCount").getAsInt();

        ArrayList<Chest> chests = new ArrayList<>();
        int x;
        int y;
        int gold;
        int itemsCount;
        String itemName;

        for (int i = 0; i < chestsCount; i++)
        {
            jsonObject = jsonElement.getAsJsonObject();
            jsonArray = jsonObject.get("chests").getAsJsonArray();
            jsonObject = jsonArray.get(i).getAsJsonObject();
            x = jsonObject.get("x").getAsInt();
            y = jsonObject.get("y").getAsInt();
            gold = jsonObject.get("gold").getAsInt();
            itemsCount = jsonObject.get("itemsCount").getAsInt();
            jsonArray = jsonObject.get("items").getAsJsonArray();
            LinkedList<Item> items = new LinkedList<>();
            for (int j = 0; j < itemsCount; j++)
            {
                itemName = jsonArray.get(j).getAsString();
                items.add(ItemParser.parseItem(itemName));
            }
            chests.add(new Chest(new Vector2d(x, y), gold, items));
        }

        return chests;
    }
}
