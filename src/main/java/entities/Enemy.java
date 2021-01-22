package entities;

public class Enemy extends Entity
{
    private int minDamage;

    private int maxDamage;

    private boolean areaAttack;

    public Enemy(String name, int health, Race race, int level, int experience)
    {
        super(name, health, race, level, experience);
    }

    public Enemy(String name, int health, Race race, int level, int experience, int minDamage, int maxDamage, boolean areaAttack)
    {
        super(name, health, race, level, experience);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.areaAttack = areaAttack;
    }

    public void setAreaAttack(boolean areaAttack)
    {
        this.areaAttack = areaAttack;
    }

    public int getMinDamage()
    {
        return minDamage;
    }

    public int getMaxDamage()
    {
        return maxDamage;
    }

    public boolean isAreaAttack()
    {
        return areaAttack;
    }

    public int getDamage()
    {
        return (int)(Math.random()*(maxDamage - minDamage + 1)) + minDamage;
    }

    public String toString()
    {
        return "Enemy{" +
                "areaAttack=" + areaAttack +
                "} " + super.toString();
    }
}
