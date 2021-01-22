package skills;

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
        return new LockpickingSkill(this.name, this.grandmasterClasses, this.value, this.level);
    }
}
