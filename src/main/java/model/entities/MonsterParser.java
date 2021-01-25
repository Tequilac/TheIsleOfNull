package model.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.races.Race;
import model.races.RaceParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MonsterParser
{
    public static ArrayList<Object> parseMonster(String name) throws FileNotFoundException
    {
        ArrayList<Object> attributes = new ArrayList<>(5);
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/monsters/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int health = jsonObject.get("health").getAsInt();
        attributes.add(health);
        String raceStr = jsonObject.get("race").getAsString();
        Race race = RaceParser.parseRace(false, raceStr);
        attributes.add(race);
        int minDamage = jsonObject.get("minDamage").getAsInt();
        attributes.add(minDamage);
        int maxDamage = jsonObject.get("maxDamage").getAsInt();
        attributes.add(maxDamage);
        boolean areaAttack = jsonObject.get("areaAttack").getAsBoolean();
        attributes.add(areaAttack);
        return attributes;
    }

    public static Enemy read(String name, int level, int experience) throws FileNotFoundException
    {
        ArrayList<Object> attributes = MonsterParser.parseMonster(name);
        return new Enemy(name, (int)attributes.get(0), (Race)attributes.get(1), level, experience, (int)attributes.get(2), (int)attributes.get(3), (boolean)attributes.get(4));
    }
}
