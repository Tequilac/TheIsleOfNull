package entities;


public class Entity
{
    protected String name;

    protected int health;

    protected Race race;

    protected int level;

    protected int experience;

    public Entity(String name, int health, Race race, int level, int experience)
    {
        this.name = name;
        this.health = health;
        this.race = race;
        this.level = level;
        this.experience = experience;
    }

    public String getName()
    {
        return name;
    }

    public int getHealth()
    {
        return health;
    }

    public Race getRace()
    {
        return race;
    }

    public int getLevel()
    {
        return level;
    }

    public int getExperience()
    {
        return experience;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setRace(Race race)
    {
        this.race = race;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setExperience(int experience)
    {
        this.experience = experience;
    }

    public void damage(int value)
    {
        if(health > value)
            health = health - value;
        else
            health = 0;
    }

    public boolean isDead()
    {
        return health == 0;
    }

    @Override
    public String toString()
    {
        return "Entity{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", race=" + race +
                ", level=" + level +
                ", experience=" + experience +
                '}';
    }
}
