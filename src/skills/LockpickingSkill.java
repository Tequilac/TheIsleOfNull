package skills;

import entities.Class;

import java.util.ArrayList;

public class LockpickingSkill extends Skill
{
    public LockpickingSkill(String name, ArrayList<String> classes, int value, int level)
    {
        super(name, classes, value, level);
    }

    @Override
    public Skill cloneSkill()
    {
        return new LockpickingSkill(this.name, this.classes, this.value, this.level);
    }
}
