package model.skills;

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

    public boolean canBeTaught(Skill characterSkill)
    {
        switch(levelTaught)
        {
            case 1:
                return false;
            case 2:
                return characterSkill.getLevel() == 1 && characterSkill.getValue() > 3;
            case 3:
                return characterSkill.getLevel() == 2 && characterSkill.getValue() > 6;
            case 4:
                return characterSkill.getLevel() == 3 && characterSkill.getValue() > 9;
        }
        return false;
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
