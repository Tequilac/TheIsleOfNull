package inhabitants;

import map.Vector2d;
import skills.TaughtSkill;

import java.util.ArrayList;

public class Trainer extends Inhabitant
{
    private ArrayList<TaughtSkill> taughtSkills;

    public Trainer(String name, String description, Vector2d position, ArrayList<TaughtSkill> taughtSkills)
    {
        super(name, description, position);
        this.taughtSkills = taughtSkills;
    }
}
