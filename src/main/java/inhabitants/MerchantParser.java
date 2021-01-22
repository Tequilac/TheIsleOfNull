package inhabitants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import items.Item;
import items.ItemParser;
import map.Vector2d;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MerchantParser
{
    public static Merchant parseMerchant(String name) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/inhabitants/merchants/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        int x;
        int y;
        String description;
        int itemsAmount;
        String itemName;

        x = jsonObject.get("x").getAsInt();
        y = jsonObject.get("y").getAsInt();
        description = jsonObject.get("description").getAsString();
        itemsAmount = jsonObject.get("itemsAmount").getAsInt();

        ArrayList<Item> offeredItems = new ArrayList<>(itemsAmount);
        jsonArray = jsonObject.get("items").getAsJsonArray();
        for(int i = 0; i < itemsAmount; i++)
        {
            itemName = jsonArray.get(i).getAsString();
            offeredItems.add(ItemParser.parseItem(itemName));
        }
        return new Merchant(name, description, new Vector2d(x, y), offeredItems);
    }
}
