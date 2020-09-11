package skills;

import entities.Class;

import java.util.ArrayList;

public class DefensiveItemSkill extends ItemSkill
{
    public DefensiveItemSkill(String name, ArrayList<String> classes, int value, int level)
    {
        super(name, classes, value, level);
    }

    @Override
    public int applyModifier(int baseValue)
    {
        return baseValue;
    }
}
