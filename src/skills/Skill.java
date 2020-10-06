package skills;

import entities.Class;

import java.util.ArrayList;

public class Skill
{
    protected String name;

    protected ArrayList<String> classes;

    protected int value;

    protected int level;

    public Skill(String name, ArrayList<String> classes, int value, int level)
    {
        this.name = name;
        this.classes = classes;
        this.value = value;
        this.level = level;
    }

    public void increaseValue()
    {
        value++;
    }

    public void increaseLevel()
    {
        level++;
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
