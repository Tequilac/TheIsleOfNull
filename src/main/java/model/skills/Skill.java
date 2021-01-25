package model.skills;

import java.util.ArrayList;

public abstract class Skill
{
    protected String name;

    protected ArrayList<String> grandmasterClasses;

    protected int value;

    protected int level;

    public Skill(String name, ArrayList<String> grandmasterClasses, int value, int level)
    {
        this.name = name;
        this.grandmasterClasses = grandmasterClasses;
        this.value = value;
        this.level = level;
    }

    public abstract Skill cloneSkill();

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

    public ArrayList<String> getGrandmasterClasses()
    {
        return grandmasterClasses;
    }
}
