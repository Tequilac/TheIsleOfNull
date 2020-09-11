package entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.Game;
import skills.Skill;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClassParser
{
    public static Class parseClass(Game game, String name) throws IOException
    {
        int [] attributes = new int[7];
        boolean usesMagic;
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/classes/" + name));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        attributes[0] = jsonObject.get("might").getAsInt();
        attributes[1] = jsonObject.get("intellect").getAsInt();
        attributes[2] = jsonObject.get("personality").getAsInt();
        attributes[3] = jsonObject.get("endurance").getAsInt();
        attributes[4] = jsonObject.get("accuracy").getAsInt();
        attributes[5] = jsonObject.get("speed").getAsInt();
        attributes[6] = jsonObject.get("luck").getAsInt();
        usesMagic = jsonObject.get("usesMagic").getAsBoolean();

        ArrayList<Skill> skills = new ArrayList<>(3);
        JsonArray jsonArray = jsonObject.get("skills").getAsJsonArray();
        for(int i = 0; i < 3; i++)
        {
            skills.add(game.getSkill(jsonArray.get(i).getAsString()));
        }

        return new Class(name.substring(0, name.length()-5), attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5], attributes[6], usesMagic, skills);
    }
}