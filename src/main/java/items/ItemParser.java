package items;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class ItemParser
{
    public static Item parseItem(String name) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/items/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int itemCost;
        String itemType;
        String type;
        int itemMinDamage;
        int itemMaxDamage;
        int range;
        int itemDamageReduction;
        String spellName;
        Item item;

        type = jsonObject.get("type").getAsString();
        itemCost = jsonObject.get("cost").getAsInt();
        itemType = jsonObject.get("itemType").getAsString();
        switch (itemType)
        {
            case "OffensiveItem":
                itemMinDamage = jsonObject.get("minDamage").getAsInt();
                itemMaxDamage = jsonObject.get("maxDamage").getAsInt();
                if(jsonObject.get("ranged").getAsBoolean())
                {
                    range = jsonObject.get("range").getAsInt();
                    item = new OffensiveRangedItem(itemCost, name, type, itemMinDamage, itemMaxDamage, range);
                }
                else
                    item = new OffensiveItem(itemCost, name, type, itemMinDamage, itemMaxDamage);
                break;
            case "DefensiveItem":
                itemDamageReduction = jsonObject.get("damageReduction").getAsInt();
                item = new DefensiveItem(itemCost, name, type, itemDamageReduction);
                break;
            case "SpellBookItem":
                spellName = jsonObject.get("spellName").getAsString();
                item = new SpellBookItem(itemCost, name, type, spellName);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + itemType);
        }
        return item;
    }
}
