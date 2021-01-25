package model.skills;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.magic.MagicSchool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class SkillParser
{
    public static Skill parseSkill(String name)
    {
        try
        {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/skills/" + name + ".json"));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String type;
            Skill skill;

            int classNumber = jsonObject.get("classNumber").getAsInt();
            JsonArray jsonArray = jsonObject.get("grandmasterClasses").getAsJsonArray();

            ArrayList<String> grandmasterClasses = new ArrayList<>(classNumber);

            for(int i = 0; i < classNumber; i++)
            {
                grandmasterClasses.add(jsonArray.get(i).getAsString());
            }

            type = jsonObject.get("type").getAsString();
            skill = switch(type)
                    {
                        case "OffensiveItemSkill" -> new OffensiveItemSkill(name, grandmasterClasses, 1, 1);
                        case "DefensiveItemSkill" -> new DefensiveItemSkill(name, grandmasterClasses, 1, 1);
                        case "MagicSchoolSkill" -> new MagicSchoolSkill(name, grandmasterClasses, 1, 1, MagicSchool.valueOf(jsonObject.get("MagicSchool").getAsString()));
                        case "LockpickingSkill" -> new LockpickingSkill(name, grandmasterClasses, 1, 1);
                        default -> throw new IllegalStateException("Unexpected value: " + type);
                    };
            return skill;
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
