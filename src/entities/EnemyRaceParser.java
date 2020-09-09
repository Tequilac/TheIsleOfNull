package entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class EnemyRaceParser
{
    public static Race parseRace(String name) throws FileNotFoundException
    {
        int [] attributes = new int[7];
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/enemyRaces/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        attributes[0] = jsonObject.get("might").getAsInt();
        attributes[1] = jsonObject.get("intellect").getAsInt();
        attributes[2] = jsonObject.get("personality").getAsInt();
        attributes[3] = jsonObject.get("endurance").getAsInt();
        attributes[4] = jsonObject.get("accuracy").getAsInt();
        attributes[5] = jsonObject.get("speed").getAsInt();
        attributes[6] = jsonObject.get("luck").getAsInt();
        return new Race(name, attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6]);
    }
}
