package entities;

import magic.Spell;

import java.util.LinkedList;

public class MagicCharacter extends Character
{
    private LinkedList<Spell> spells;

    public MagicCharacter(String name, int health, Race race, int level, int experience, Class characterClass, int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, LinkedList<Spell> spells)
    {
        super(name, health, race, level, experience, characterClass, might, intellect, personality, endurance, accuracy, speed, luck);
        this.spells = spells;
    }

    public MagicCharacter(String name, int health, int maxHealth, int mana, int maxMana, Race race, int level, int experience, Class characterClass, int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, LinkedList<Spell> spells)
    {
        super(name, health, maxHealth, mana, maxMana, race, level, experience, characterClass, might, intellect, personality, endurance, accuracy, speed, luck);
        this.spells = spells;
    }

    public MagicCharacter(String name, int health, Race race, int level, int experience, Class characterClass, LinkedList<Spell> spells)
    {
        super(name, health, race, level, experience, characterClass);
        this.spells = spells;
    }

    public void learnSpell(Spell spell)
    {
        spells.add(spell);
    }
}
