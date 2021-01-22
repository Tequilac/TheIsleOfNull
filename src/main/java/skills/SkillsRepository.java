package skills;


import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class SkillsRepository
{

    private final Map<String, Skill> knownSkills = new HashMap<>();

    public Skill getSkill(String skillName)
    {
        Skill skill = knownSkills.get(skillName);
        if(skill == null)
        {
            skill = SkillParser.parseSkill(skillName);
            knownSkills.put(skillName, skill);
        }
        return skill;
    }
}
