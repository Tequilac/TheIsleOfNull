package entities;

import skills.Skill;

import java.util.ArrayList;

public class Class
{
    private final String name;

    private final int might;

    private final int intellect;

    private final int personality;

    private final int endurance;

    private final int accuracy;

    private final int speed;

    private final int luck;

    private final boolean usesMagic;

    private final ArrayList<Skill> skills;

    public Class(String name, int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, boolean usesMagic, ArrayList<Skill> skills)
    {
        this.name = name;
        this.might = might;
        this.intellect = intellect;
        this.personality = personality;
        this.endurance = endurance;
        this.accuracy = accuracy;
        this.speed = speed;
        this.luck = luck;
        this.usesMagic = usesMagic;
        this.skills = skills;
    }

    public String getName()
    {
        return name;
    }

    public int getMight()
    {
        return might;
    }

    public int getIntellect()
    {
        return intellect;
    }

    public int getPersonality()
    {
        return personality;
    }

    public int getEndurance()
    {
        return endurance;
    }

    public int getAccuracy()
    {
        return accuracy;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getLuck()
    {
        return luck;
    }

    public boolean usesMagic()
    {
        return usesMagic;
    }

    public ArrayList<Skill> getSkills()
    {
        return skills;
    }
}
