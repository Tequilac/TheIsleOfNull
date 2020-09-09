package skills;

import entities.Class;

import java.util.ArrayList;

public abstract class ItemSkill extends Skill
{
    private String targetName;

    public ItemSkill(String name, ArrayList<Class> classes, int value, int level)
    {
        super(name, classes, value, level);
    }

    public abstract int applyModifier(int baseValue);

    public String getTargetName()
    {
        return targetName;
    }
}
