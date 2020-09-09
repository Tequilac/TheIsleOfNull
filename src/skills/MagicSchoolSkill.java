package skills;

import entities.Class;
import magic.MagicSchool;

import java.util.ArrayList;

public class MagicSchoolSkill extends Skill
{
    MagicSchool magicSchool;

    public MagicSchoolSkill(String name, ArrayList<Class> classes, int value, int level, MagicSchool magicSchool)
    {
        super(name, classes, value, level);
        this.magicSchool = magicSchool;
    }
}
