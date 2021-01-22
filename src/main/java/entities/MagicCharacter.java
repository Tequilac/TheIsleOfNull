package entities;

import classes.Class;
import magic.Spell;
import races.Race;

import java.util.LinkedList;

public class MagicCharacter extends Character
{
    private int mana;

    private int maxMana;

    private LinkedList<Spell> spells;

    public MagicCharacter(String name, int health, int maxHealth, int mana, int maxMana, Race race, int level, int experience, Class characterClass,
                          int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, LinkedList<Spell> spells, int skillPoints)
    {
        super(name, health, maxHealth, race, level, experience, characterClass,
                might, intellect, personality, endurance, accuracy, speed, luck, skillPoints);
        this.mana = mana;
        this.maxMana = maxMana;
        this.spells = spells;
    }

    public MagicCharacter(Character character)
    {
        super(character.getName(), character.getHealth(), character.getMaxHealth(), character.getRace(),
                character.getLevel(), character.getExperience(), character.getCharacterClass(),
                character.getMight(), character.getIntellect(), character.getPersonality(), character.getEndurance(),
                character.getAccuracy(), character.getSpeed(), character.getLuck(), character.getSkillPoints());
        this.mana = 100;
        this.maxMana = 100;
        this.spells = new LinkedList<>();
    }

    public void removeMana(int mana)
    {
        this.mana -= mana;
    }

    public void learnSpell(Spell spell)
    {
        spells.add(spell);
    }

    public int getMana()
    {
        return mana;
    }

    public int getMaxMana()
    {
        return maxMana;
    }

    public LinkedList<Spell> getSpells()
    {
        return spells;
    }
}
