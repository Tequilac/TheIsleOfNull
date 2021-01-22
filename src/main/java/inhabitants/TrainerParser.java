package inhabitants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import items.*;
import map.Vector2d;
import skills.TaughtSkill;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrainerParser
{
    public static Trainer parseTrainer(String name) throws IOException
    {
        JsonElement jsonElement = JsonParser.parseReader(new FileReader("src/main/resources/inhabitants/trainers/" + name + ".json"));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray;

        int x;
        int y;
        String description;
        int skillsAmount;
        String skillName;
        int levelTaught;
        int cost;

        x = jsonObject.get("x").getAsInt();
        y = jsonObject.get("y").getAsInt();
        description = jsonObject.get("description").getAsString();
        skillsAmount = jsonObject.get("skillsAmount").getAsInt();

        ArrayList<TaughtSkill> taughtSkills = new ArrayList<>(skillsAmount);
        jsonArray = jsonObject.get("skills").getAsJsonArray();
        for(int i = 0; i < skillsAmount; i++)
        {
            jsonObject = jsonArray.get(i).getAsJsonObject();
            skillName = jsonObject.get("name").getAsString();
            levelTaught = jsonObject.get("level").getAsInt();
            cost = jsonObject.get("cost").getAsInt();
            taughtSkills.add(new TaughtSkill(skillName, levelTaught, cost));
        }
        return new Trainer(name, description, new Vector2d(x, y), taughtSkills);
    }
}
