package entities;

public class Class
{
    private String name;

    private int might;

    private int intellect;

    private int personality;

    private int endurance;

    private int accuracy;

    private int speed;

    private int luck;

    private boolean usesMagic;

    public Class(String name, int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, boolean usesMagic)
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
}
