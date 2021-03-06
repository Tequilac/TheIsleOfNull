package model.inhabitants;

import model.map.Vector2d;
import model.skills.TaughtSkill;

import java.util.ArrayList;

public class Trainer extends Inhabitant
{
    private ArrayList<TaughtSkill> taughtSkills;

    public Trainer(String name, String description, Vector2d position, ArrayList<TaughtSkill> taughtSkills)
    {
        super(name, description, position);
        this.taughtSkills = taughtSkills;
    }

    public ArrayList<TaughtSkill> getTaughtSkills()
    {
        return taughtSkills;
    }
}
