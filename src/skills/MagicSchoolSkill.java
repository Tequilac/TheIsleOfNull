package skills;

import entities.Class;
import magic.MagicSchool;

import java.util.ArrayList;

public class MagicSchoolSkill extends Skill
{
    MagicSchool magicSchool;

    public MagicSchoolSkill(String name, ArrayList<String> classes, int value, int level, MagicSchool magicSchool)
    {
        super(name, classes, value, level);
        this.magicSchool = magicSchool;
    }

    public Skill cloneSkill()
    {
        return new MagicSchoolSkill(this.name, this.classes, this.value, this.level, this.magicSchool);
    }
}
