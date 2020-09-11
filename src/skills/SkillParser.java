package skills;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Class;
import items.*;
import magic.MagicSchool;
import main.Game;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SkillParser
{
    public static Skill parseSkill(Game game, String name) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("res/skills/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String type;
        Skill skill;

        int classNumber = jsonObject.get("classNumber").getAsInt();
        JsonArray jsonArray = jsonObject.get("classes").getAsJsonArray();

        ArrayList<String> classes = new ArrayList<>(classNumber);

        for(int i = 0; i < classNumber; i++)
        {
            classes.add(jsonArray.get(i).getAsString());
        }

        type = jsonObject.get("type").getAsString();
        switch (type)
        {
            case "OffensiveItemSkill":
                skill = new OffensiveItemSkill(name, classes, 1, 1);
                break;
            case "DefensiveItemSkill":
                skill = new DefensiveItemSkill(name, classes, 1, 1);
                break;
            case "MagicSchoolSkill":
                skill = new MagicSchoolSkill(name, classes, 1, 1, MagicSchool.valueOf(jsonObject.get("MagicSchool").getAsString()));
                break;
            case "LockpickingSkill":
                skill = new LockpickingSkill(name, classes, 1, 1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return skill;
    }
}
