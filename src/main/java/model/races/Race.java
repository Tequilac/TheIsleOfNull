package model.races;

public class  Race
{
    private final String name;

    private final int mightModifier;

    private final int intellectModifier;

    private final int personalityModifier;

    private final int enduranceModifier;

    private final int accuracyModifier;

    private final int speedModifier;

    private final int luckModifier;

    public Race(String name, int mightModifier, int intellectModifier, int personalityModifier, int enduranceModifier, int accuracyModifier, int speedModifier, int luckModifier)
    {
        this.name = name;
        this.mightModifier = mightModifier;
        this.intellectModifier = intellectModifier;
        this.personalityModifier = personalityModifier;
        this.enduranceModifier = enduranceModifier;
        this.accuracyModifier = accuracyModifier;
        this.speedModifier = speedModifier;
        this.luckModifier = luckModifier;
    }

    public int getMightModifier()
    {
        return mightModifier;
    }

    public int getIntellectModifier()
    {
        return intellectModifier;
    }

    public int getPersonalityModifier()
    {
        return personalityModifier;
    }

    public int getEnduranceModifier()
    {
        return enduranceModifier;
    }

    public int getAccuracyModifier()
    {
        return accuracyModifier;
    }

    public int getSpeedModifier()
    {
        return speedModifier;
    }

    public int getLuckModifier()
    {
        return luckModifier;
    }

    public String getName()
    {
        return name;
    }
}
