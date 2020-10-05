package entities;

import equipment.Equipment;
import equipment.EquipmentType;
import items.Item;
import items.OffensiveItem;
import skills.ItemSkill;
import skills.Skill;

import java.util.ArrayList;

public class Character extends Entity
{
    private Class characterClass;

    private int maxHealth;

    private int mana;

    private int maxMana;

    private int might;

    private int intellect;

    private int personality;

    private int endurance;

    private int accuracy;

    private int speed;

    private int luck;

    private Equipment leftHand;

    private Equipment rightHand;

    private Equipment head;

    private Equipment torso;

    private ArrayList<Skill> skills;

    private int skillPoints;

    public Character(String name, int health, int maxHealth, int mana, int maxMana, Race race, int level, int experience, Class characterClass,
                     int might, int intellect, int personality, int endurance, int accuracy, int speed, int luck, int skillPoints)
    {
        super(name, health, race, level, experience);
        this.characterClass = characterClass;
        this.maxHealth = maxHealth;
        this.mana = mana;
        this.maxMana = maxMana;
        this.might = might;
        this.intellect = intellect;
        this.personality = personality;
        this.endurance = endurance;
        this.accuracy = accuracy;
        this.speed = speed;
        this.luck = luck;
        this.skills = characterClass.getSkills();
        this.skillPoints = skillPoints;
        Item[] items = {null, null, null, null};
        initEquipment(items);
    }

    public Character(String name, Race race, int level, int experience, Class characterClass)
    {
        super(name, 0, race, level, experience);
        this.setClass(characterClass);
        this.health = 100;
        this.maxHealth = 100;
        this.mana = 100;
        this.maxMana = 100;
        this.skillPoints = 0;
        Item[] items = {null, null, null, null};
        initEquipment(items);
    }

    public void initEquipment(Item[] items)
    {
        head = new Equipment(EquipmentType.Head, items[0]);
        leftHand = new Equipment(EquipmentType.LeftHand, items[1]);
        rightHand = new Equipment(EquipmentType.RightHand, items[2]);
        torso = new Equipment(EquipmentType.Torso, items[3]);
    }

    public int getDamage()
    {
        int value = might/3;
        if(rightHand != null && rightHand.getItem() instanceof OffensiveItem)
        {
            value += rightHand.getValue();
            ItemSkill skill = findSkillForItem(rightHand.getItem().getType());
            if(skill != null)
            {
                value = skill.applyModifier(value);
            }
        }
        return value;
    }

    public ItemSkill findSkillForItem(String type)
    {
        for(Skill skill : skills)
        {
            if(skill instanceof ItemSkill && type.equals(((ItemSkill) skill).getTargetName()))
                return (ItemSkill) skill;
        }
        return null;
    }

    public void damage(int value)
    {
        value = value - endurance/3;
        if(value < 0)
            value = 0;
        if(health > value)
            health = health - value;
        else
            health = 0;
    }

    public void addExperience(int experience)
    {
        this.experience += experience;
        if(this.experience > 100)
        {
            this.experience -= 100;
            levelUp();
        }
    }

    public void levelUp()
    {
        level++;
    }

    public Class getCharacterClass()
    {
        return characterClass;
    }

    public int getMana()
    {
        return mana;
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

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getMaxMana()
    {
        return maxMana;
    }

    public ArrayList<Skill> getSkills()
    {
        return skills;
    }

    public Equipment getLeftHand()
    {
        return leftHand;
    }

    public Equipment getRightHand()
    {
        return rightHand;
    }

    public Equipment getHead()
    {
        return head;
    }

    public Equipment getTorso()
    {
        return torso;
    }

    public int getSkillPoints()
    {
        return skillPoints;
    }

    public void setMana(int mana)
    {
        this.mana = mana;
    }

    public void setMight(int might)
    {
        this.might = might;
    }

    public void setIntellect(int intellect)
    {
        this.intellect = intellect;
    }

    public void setPersonality(int personality)
    {
        this.personality = personality;
    }

    public void setEndurance(int endurance)
    {
        this.endurance = endurance;
    }

    public void setAccuracy(int accuracy)
    {
        this.accuracy = accuracy;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setLuck(int luck)
    {
        this.luck = luck;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public void setMaxMana(int maxMana)
    {
        this.maxMana = maxMana;
    }

    public void setClass(Class characterClass)
    {
        this.characterClass = characterClass;
        this.might = characterClass.getMight() + race.getMightModifier();
        this.intellect = characterClass.getIntellect() + race.getIntellectModifier();
        this.personality = characterClass.getPersonality() + race.getPersonalityModifier();
        this.endurance = characterClass.getEndurance() + race.getEnduranceModifier();
        this.accuracy = characterClass.getAccuracy() + race.getAccuracyModifier();
        this.speed = characterClass.getSpeed() + race.getSpeedModifier();
        this.luck = characterClass.getLuck() + race.getLuckModifier();
        this.skills = characterClass.getSkills();
    }

    public void setRace(Race race)
    {
        this.race = race;
        setClass(characterClass);
    }

    @Override
    public String toString()
    {
        return "Character{" +
                "characterClass=" + characterClass +
                ", might=" + might +
                ", intellect=" + intellect +
                ", personality=" + personality +
                ", endurance=" + endurance +
                ", accuracy=" + accuracy +
                ", speed=" + speed +
                ", luck=" + luck +
                "} " + super.toString();
    }
}
