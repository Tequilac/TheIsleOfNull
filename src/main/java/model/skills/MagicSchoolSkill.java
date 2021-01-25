package model.skills;

import model.magic.MagicSchool;

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
        return new MagicSchoolSkill(this.name, this.grandmasterClasses, this.value, this.level, this.magicSchool);
    }
}
