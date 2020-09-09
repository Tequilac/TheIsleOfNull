package skills;

import entities.Class;

import java.util.ArrayList;

public class OffensiveItemSkill extends ItemSkill
{

    public OffensiveItemSkill(String name, ArrayList<Class> classes, int value, int level)
    {
        super(name, classes, value, level);
    }

    @Override
    public int applyModifier(int baseValue)
    {
        return baseValue + level*value;
    }
}
