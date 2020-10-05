package entities;

import magic.Spell;

import java.util.LinkedList;

public class MagicCharacter extends Character
{
    private LinkedList<Spell> spells;

    public MagicCharacter(String name, int health, int maxHealth, int mana, int maxMana, Race race, int level, int experience, Class characterClass,
                          int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, LinkedList<Spell> spells, int skillPoints)
    {
        super(name, health, maxHealth, mana, maxMana, race, level, experience, characterClass, might, intellect, personality, endurance, accuracy, speed, luck, skillPoints);
        this.spells = spells;
    }

    public MagicCharacter(String name, Race race, int level, int experience, Class characterClass, LinkedList<Spell> spells)
    {
        super(name, race, level, experience, characterClass);
        this.spells = spells;
    }

    public MagicCharacter(Character character)
    {
        super(character.getName(), character.getHealth(), character.getMaxHealth(), character.getMana(), character.getMaxMana(),
                character.getRace(), character.getLevel(), character.getExperience(), character.getCharacterClass(),
                character.getMight(), character.getIntellect(), character.getPersonality(), character.getEndurance(),
                character.getAccuracy(), character.getSpeed(), character.getLuck(), character.getSkillPoints());
        this.spells = new LinkedList<>();
    }

    public void learnSpell(Spell spell)
    {
        spells.add(spell);
    }
}
