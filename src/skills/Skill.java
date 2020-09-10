package skills;

import entities.Class;

import java.util.ArrayList;

public class Skill
{
    protected String name;

    protected ArrayList<Class> classes;

    protected int value;

    protected int level;

    public Skill(String name, ArrayList<Class> classes, int value, int level)
    {
        this.name = name;
        this.classes = classes;
        this.value = value;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public int getValue()
    {
        return value;
    }

    public int getLevel()
    {
        return level;
    }
}
