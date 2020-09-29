package skills;

public class TaughtSkill
{
    private final String name;

    private final int levelTaught;

    private final int cost;

    public TaughtSkill(String name, int levelTaught, int cost)
    {
        this.name = name;
        this.levelTaught = levelTaught;
        this.cost = cost;
    }

    public String getName()
    {
        return name;
    }

    public int getLevelTaught()
    {
        return levelTaught;
    }

    public int getCost()
    {
        return cost;
    }
}
