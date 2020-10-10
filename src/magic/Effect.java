package magic;

public class Effect
{
    private String name;

    private int duration;

    public Effect(String name, int duration)
    {
        this.name = name;
        this.duration = duration;
    }

    public String getName()
    {
        return name;
    }

    public int getDuration()
    {
        return duration;
    }
}
