package skills;

import entities.Class;
import items.OffensiveItem;

import java.util.ArrayList;

public class OffensiveItemSkill extends ItemSkill
{
    public OffensiveItemSkill(String name, ArrayList<String> classes, int value, int level)
    {
        super(name, classes, value, level);
    }

    @Override
    public Skill cloneSkill()
    {
        return new OffensiveItemSkill(this.name, this.classes, this.value, this.level);
    }

    @Override
    public int applyModifier(int baseValue)
    {
        return baseValue + level*value;
    }
}
