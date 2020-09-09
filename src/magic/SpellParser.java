package magic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;

public class SpellParser
{
    public static Spell parseSpell(String filename) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/spells/" + filename + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        MagicSchool magicSchool = MagicSchool.valueOf(jsonObject.get("school").getAsString());
        String description = jsonObject.get("description").getAsString();
        int manaCost = jsonObject.get("cost").getAsInt();
        int spellLevel = jsonObject.get("level").getAsInt();
        int value;

        Spell spell = null;

        switch (jsonObject.get("type").getAsString())
        {
            case "DamageSpell":
                value = jsonObject.get("value").getAsInt();
                spell = new DamageSpell();
                break;

        }
        return spell;
    }
}
