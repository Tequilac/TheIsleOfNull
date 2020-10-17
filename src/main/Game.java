package main;

import entities.Character;
import entities.Class;
import entities.*;
import inhabitants.Inhabitant;
import inhabitants.Merchant;
import items.Item;
import magic.DamageSpell;
import magic.Spell;
import map.*;
import map.districts.District;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.Quest;
import quests.QuestGiver;
import quests.QuestStatus;
import saves.Save;
import skills.Skill;
import skills.SkillParser;
import visuals.Frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Game
{
    private Frame frame;

    private File currentSave;

    private District currentDistrict;

    private ArrayList<Character> characters;

    private Team team;

    private int currentMember;

    private String teamInfo;

    private Chest openedChest;

    private QuestGiver metQuestGiver;

    private Inhabitant metInhabitant;

    private Group chosenEnemies;

    private Character currentCharacter;

    private Map<String, Race> knownRaces = new HashMap<>();

    private Map<String, Race> knownEnemyRaces = new HashMap<>();

    private Map<String, Class> knownClasses = new HashMap<>();

    private Map<String, Skill> knownSkills = new HashMap<>();

    private boolean onObject;

    private boolean onEntrance;

    public Game() throws IOException
    {
        characters = new ArrayList<>(4);
        loadClasses(new File("res/classes"));
        loadRaces(new File("res/races"));
        createStartingCharacters();
        currentDistrict = MapParser.parseMap("W11");
        currentMember = 0;
        onObject = false;
        onEntrance = false;
    }

    public void loadClasses(File folder) throws IOException
    {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles()))
        {
            getClass(fileEntry.getName().substring(0, fileEntry.getName().length() - 5));
        }
    }

    public void loadRaces(File folder) throws FileNotFoundException
    {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles()))
        {
            getRace(fileEntry.getName().substring(0, fileEntry.getName().length() - 5));
        }
    }

    public void createStartingCharacters()
    {
        for (int i = 0; i < 4; i++)
        {
            Character character = new Character("Choose name", knownRaces.get("Human"), 0, 0, knownClasses.get("Archer"));
            characters.add(character);
        }
    }

    public void loadSaveGame(File filename) throws IOException
    {
        this.currentSave = filename;
        Save.parseSave(this, filename);
        this.currentCharacter = characters.get(0);
    }

    public void saveGame() throws IOException
    {
        Save.saveGame(this, currentSave);
    }

    public void createTeam()
    {
        if(this.team == null)
        {
            for(int i = 0; i < 4; i++)
            {
                if(characters.get(i).getCharacterClass().usesMagic())
                {
                    characters.set(i, new MagicCharacter(characters.get(i)));
                }
            }
            this.team = new Team(new Vector2d(32, 34), MapDirection.North, characters);
            ((World) currentDistrict).updateVisibleTiles(team.getPosition());
        }
        currentCharacter = characters.get(0);
    }

    public boolean move(MoveDirection direction)
    {
        return team.move(direction, currentDistrict);
    }

    public String changeMap(MoveDirection direction)
    {
        MapDirection changeDirection = team.changeMap(direction, currentDistrict);
        if(changeDirection == null)
            return null;
        if(currentDistrict instanceof World)
        {
            switch(changeDirection)
            {
                case North:
                    return "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2))) + 1);
                case South:
                    return "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2))) - 1);
                case West:
                    return "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1))) + 1) + currentDistrict.getName().charAt(2);
                case East:
                    return "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1))) - 1) + currentDistrict.getName().charAt(2);
                default:
                    return null;
            }
        }
        else
        {
            switch(changeDirection)
            {
                case North:
                case South:
                case West:
                case East:
                    return "W" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2);
                default:
                    return null;
            }
        }
    }

    public void tick(MoveDirection direction) throws IOException
    {
        String newMap = changeMap(direction);
        if(newMap != null)
            loadNewWorldMap(newMap);
        if(move(direction))
            currentDistrict.monstersMakeDecisions(team.getPosition());

        checkForMonsters();

        frame.updateEnemies();

        checkForObjects();

        checkForEntrances();

        frame.updateButtons(onObject, onEntrance);
    }

    public void checkForMonsters()
    {
        MapDirection direction = team.getMapDirection();

        Vector2d position = team.getPosition();
        Vector2d left = position.add(direction.getPrevious().toVector2d());
        Vector2d right = position.add(direction.getNext().toVector2d());

        for (int i = 0; i < 5; i++)
        {
            for(Group group : currentDistrict.getEnemies())
            {
                if(group.getPosition().equals(left) ||
                        group.getPosition().equals(position) ||
                        group.getPosition().equals(right))
                {
                    chosenEnemies = group;
                    return;
                }
            }
            left = left.add(direction.toVector2d());
            position = position.add(direction.toVector2d());
            right = right.add(direction.toVector2d());
        }
        chosenEnemies = null;
    }

    public void checkForObjects()
    {
        if(currentDistrict.getChests() != null)
            for(Chest chest : currentDistrict.getChests())
            {
                if(chest.getPosition().equals(team.getPosition()))
                {
                    onObject = true;
                    return;
                }
            }
        if(currentDistrict instanceof World || currentDistrict instanceof Town)
        {
            if(currentDistrict.getQuestGivers() != null)
            {
                for(QuestGiver questGiver : currentDistrict.getQuestGivers())
                {
                    if(questGiver.getPosition().equals(team.getPosition()))
                    {
                        onObject = true;
                        return;
                    }
                }
            }
        }
        if(currentDistrict instanceof Town)
        {
            for(Inhabitant inhabitant : ((Town) currentDistrict).getInhabitants())
            {
                if(inhabitant.getPosition().equals(team.getPosition()))
                {
                    onObject = true;
                    return;
                }
            }
        }
        onObject = false;
    }

    public void checkForEntrances()
    {
        if(currentDistrict instanceof World)
        {
            if(((World) currentDistrict).isTown(team.getPosition()) || ((World) currentDistrict).isDungeon(team.getPosition()))
            {
                onEntrance = true;
                return;
            }
        }
        onEntrance = false;
    }

    public void enterLocation() throws IOException
    {
        checkForTownEnter();
        checkForDungeonEnter();
        update();
    }

    public void checkForTownEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isTown(team.getPosition()))
        {
            loadNewInnerMap("T" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void checkForDungeonEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isDungeon(team.getPosition()))
        {
            loadNewInnerMap("D" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void doAction()
    {
        if(openedChest != null)
        {
            frame.openMapPanel();
            openedChest = null;
        }
        else
        {
            checkForChest();
            if(openedChest != null)
            {
                frame.openChest();
            }
        }
        if(metQuestGiver != null)
        {
            frame.openMapPanel();
            metQuestGiver = null;
        }
        else
        {
            checkForQuestGiver();
            if(metQuestGiver != null)
            {
                frame.meetQuestGiver();
            }
        }
        if(metInhabitant != null)
        {
            frame.openMapPanel();
            metInhabitant = null;
        }
        else
        {
            checkForInhabitant();
            if(metInhabitant != null)
            {
                frame.meetInhabitant();
            }
        }
    }

    public void checkForChest()
    {
        Chest currentChest = null;
        if(currentDistrict.getChests() != null)
        {
            for(Chest chest : currentDistrict.getChests())
            {
                if(chest.getPosition().equals(team.getPosition()))
                    currentChest = chest;
            }
        }
        openedChest = currentChest;
    }

    public void checkForQuestGiver()
    {
        QuestGiver currentQuestGiver = null;
        if (currentDistrict.getQuestGivers() != null)
        {
            for(QuestGiver questGiver : currentDistrict.getQuestGivers())
            {
                if(questGiver.getPosition().equals(team.getPosition()))
                    currentQuestGiver = questGiver;
            }
        }
        metQuestGiver = currentQuestGiver;
    }

    public void checkForInhabitant()
    {
        Inhabitant currentInhabitant = null;
        if(currentDistrict instanceof Town)
        {
            if (((Town) currentDistrict).getInhabitants() != null)
            {
                for(Inhabitant inhabitant : ((Town) currentDistrict).getInhabitants())
                {
                    if(inhabitant.getPosition().equals(team.getPosition()))
                        currentInhabitant = inhabitant;
                }
            }
        }
        metInhabitant = currentInhabitant;
    }

    public void loadNewWorldMap(String newMap) throws IOException
    {
        boolean isTown = (currentDistrict instanceof Town);
        boolean isDungeon = (currentDistrict instanceof Dungeon);
        Save.saveGame(this, currentSave);
        Save.parseSave(this, currentSave);
        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
        currentDistrict = MapParser.parseMap(newMap);
        Save.parseSave(this, currentSave);
        if(isTown)
            team.setPosition(((World)currentDistrict).getTownEntrance());
        if(isDungeon)
            team.setPosition(((World)currentDistrict).getDungeonEntrance());
        frame.openMapPanel();
    }

    private void loadNewInnerMap(String newMap) throws IOException
    {
        Save.saveGame(this, currentSave);
        Save.parseSave(this, currentSave);
        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
        currentDistrict = MapParser.parseMap(newMap);
        Save.parseSave(this, currentSave);
        team.setPosition(new Vector2d(currentDistrict.getWidth() - 1, currentDistrict.getHeight() - 1));
        frame.openMapPanel();
    }

    public void teamHit()
    {
        Group enemies = currentDistrict.findEnemies(team.getPosition());
        if(enemies != null)
        {
            int experience = enemies.damageAll(team, team.getTeamMembers().get(currentMember).getDamage());
            team.distributeExperience(experience, currentMember);
            currentMember = (currentMember + 1) % 4;
            if(enemies.getEntities().size() == 0)
                currentDistrict.removeGroup(enemies);
        }
        retaliate();
    }

    public void update()
    {
        if(currentDistrict instanceof World)
        {
            ((World) currentDistrict).updateVisibleTiles(team.getPosition());
            return;
        }
        if(currentDistrict instanceof Dungeon)
            ((Dungeon) currentDistrict).updateVisibleTiles(team.getPosition());
    }

    public void takeQuest(Quest quest)
    {
        team.addActiveQuest(quest);
        quest.setQuestStatus(QuestStatus.Active);
    }

    public void completeQuest(QuestGiver questGiver, int index)
    {
        questGiver.getQuests().get(index).transferRewards(team);
        team.completeQuest(questGiver.getQuests().get(index));
        questGiver.getQuests().get(index).setQuestStatus(QuestStatus.Completed);
        questGiver.removeQuest(index);
    }

    public void buyItem(Item item)
    {
        ((Merchant)metInhabitant).getOfferedItems().remove(item);
        team.addItemToInventory(item);
    }

    public void transferItem(Item item)
    {
        openedChest.getItems().remove(item);
        team.addItemToInventory(item);
    }

    public void transferGold()
    {
        team.addGold(openedChest.getGold());
        openedChest.setGold(0);
    }

    public void castDamageSpell(DamageSpell spell)
    {
        Group enemies = currentDistrict.findEnemies(team.getPosition());
        if(enemies != null)
        {
            int experience = enemies.damageAll(team, spell.getValue());
            team.distributeExperience(experience, currentMember);
            currentMember = (currentMember + 1) % 4;
            if(enemies.getEntities().size() == 0)
                currentDistrict.removeGroup(enemies);
            ((MagicCharacter)currentCharacter).removeMana(spell.getManaCost());
        }

    }

    public void retaliate()
    {
        for (Group enemyGroup : currentDistrict.getEnemies())
        {
            if(enemyGroup.getPosition().equals(team.getPosition()))
            {
                for(Entity enemy : enemyGroup.getEntities())
                {
                    team.getTeamMembers().get(currentMember).damage(((Enemy) enemy).getDamage());
                    currentMember = (currentMember+1)%4;
                }
            }
        }
    }

    public Race getEnemyRace(String raceName) throws FileNotFoundException
    {
        Race race = knownEnemyRaces.get(raceName);
        if(race == null)
        {
            race = EnemyRaceParser.parseRace(raceName);
            knownEnemyRaces.put(raceName, race);
        }
        return race;
    }

    public Race getRace(String raceName) throws FileNotFoundException
    {
        Race race = knownRaces.get(raceName);
        if(race == null)
        {
            race = RaceParser.parseRace(raceName + ".json");
            knownRaces.put(raceName, race);
        }
        return race;
    }

    public Class getClass(String className) throws IOException
    {
        Class aClass = knownClasses.get(className);
        if(aClass == null)
        {
            aClass = ClassParser.parseClass(this, className + ".json");
            knownClasses.put(className, aClass);
        }
        return aClass;
    }

    public Skill getSkill(String skillName)
    {
        Skill skill = knownSkills.get(skillName);
        if(skill == null)
        {
            skill = SkillParser.parseSkill(this, skillName);
            knownSkills.put(skillName, skill);
        }
        return skill;
    }

    public Character getCurrentCharacter()
    {
        return currentCharacter;
    }

    public District getCurrentDistrict()
    {
        return currentDistrict;
    }

    public Map<String, Class> getClasses()
    {
        return knownClasses;
    }

    public Map<String, Race> getRaces()
    {
        return knownRaces;
    }

    public ArrayList<Character> getCharacters()
    {
        return characters;
    }

    public Team getTeam()
    {
        return team;
    }

    public String getTeamInfo()
    {
        return teamInfo;
    }

    public Chest getOpenedChest()
    {
        return openedChest;
    }

    public QuestGiver getMetQuestGiver()
    {
        return metQuestGiver;
    }

    public Inhabitant getMetInhabitant()
    {
        return metInhabitant;
    }

    public Group getChosenEnemies()
    {
        return chosenEnemies;
    }

    public void setFrame(Frame frame)
    {
        this.frame = frame;
    }

    public void setCurrentSave(File currentSave)
    {
        this.currentSave = currentSave;
    }

    public void setCurrentDistrict(District currentDistrict)
    {
        this.currentDistrict = currentDistrict;
    }

    public void setCharacters(ArrayList<Character> characters)
    {
        this.characters = characters;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public void setTeamInfo(String teamInfo)
    {
        this.teamInfo = teamInfo;
    }

    public void setCurrentMember(int currentMember)
    {
        this.currentMember = currentMember;
    }

    public void setChosenEnemies(int index)
    {
        this.chosenEnemies = currentDistrict.getEnemies().get(index);
    }

    public void setCurrentCharacter(Character currentCharacter)
    {
        this.currentCharacter = currentCharacter;
    }
}