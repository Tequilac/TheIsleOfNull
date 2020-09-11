package main;

import entities.Character;
import entities.Class;
import entities.*;
import items.Item;
import map.*;
import map.districts.District;
import map.districts.Dungeon;
import map.districts.Town;
import map.districts.World;
import quests.Quest;
import quests.QuestGiver;
import saves.Save;
import skills.Skill;
import skills.SkillParser;
import visuals.Frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game
{
    private Frame frame;

    private File currentSave;

    private District currentDistrict;

    private ArrayList<Class> classes;

    private ArrayList<Race> races;

    private ArrayList<Character> characters;

    private Team team;

    private int currentMember;

    private String teamInfo;

    private Chest openedChest;

    private QuestGiver metQuestGiver;

    private Group chosenEnemies;

    private Map<String, Race> knownRaces = new HashMap<>();

    private Map<String, Race> knownEnemyRaces = new HashMap<>();

    private Map<String, Class> knownClasses = new HashMap<>();

    private Map<String, Skill> knownSkills = new HashMap<>();

    public Game() throws IOException
    {
        classes = new ArrayList<>();
        races = new ArrayList<>();
        characters = new ArrayList<>(4);
        loadClasses(new File("res/classes"));
        loadRaces(new File("res/races"));
        createStartingCharacters();
        currentDistrict = MapParser.parseMap("W11");
        currentMember = 0;
    }

    public void loadClasses(File folder) throws IOException
    {
        for (final File fileEntry : folder.listFiles())
        {
            classes.add(ClassParser.parseClass(this, fileEntry.getName()));
        }
    }

    public void loadRaces(File folder) throws FileNotFoundException
    {
        for (final File fileEntry : folder.listFiles())
        {
            races.add(RaceParser.parseRace(fileEntry.getName()));
        }
    }

    public void createStartingCharacters()
    {
        for (int i = 0; i < 4; i++)
        {
            Character character = new Character("Choose name", 100, races.get(0), 0, 0, classes.get(0));
            characters.add(character);
        }
    }

    public void loadSaveGame(File filename) throws IOException
    {
        this.currentSave = filename;
        Save.parseSave(this, filename);
    }

    public void saveGame() throws IOException
    {
        Save.saveGame(this, currentSave);
    }

    public void createTeam()
    {
        if (this.team == null)
        {
            this.team = new Team(new Vector2d(32, 34), MapDirection.North, characters);
            ((World) currentDistrict).updateVisibleTiles(team.getPosition());
        }
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
        switch (changeDirection)
        {
            case North:
                return "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2)))+1);
            case South:
                return "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2)))-1);
            case West:
                return "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1)))+1) + currentDistrict.getName().charAt(2);
            case East:
                return "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1)))-1) + currentDistrict.getName().charAt(2);
            default:
                return null;
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

        if(chosenEnemies != null)
            frame.updateEnemies();
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
                left = left.add(direction.toVector2d());
                position = position.add(direction.toVector2d());
                right = right.add(direction.toVector2d());
            }
        }
    }

    public void checkForTownEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isTown(team.getPosition()))
        {
            loadNewTownMap("T" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void checkForTownExit() throws IOException
    {
        if(currentDistrict instanceof Town && ((Town) currentDistrict).isExit(team.getPosition()))
        {
            loadNewWorldMap("W" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void checkForDungeonEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isDungeon(team.getPosition()))
        {
            loadNewDungeonMap("D" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void checkForDungeonExit() throws IOException
    {
        if(currentDistrict instanceof Dungeon && ((Dungeon) currentDistrict).isExit(team.getPosition()))
        {
            loadNewWorldMap("W" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
            frame.getMainPanel().repaint();
        }
    }

    public void doAction() throws IOException
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
        checkForTownExit();
        checkForTownEnter();
        checkForDungeonExit();
        checkForDungeonEnter();
    }

    public void checkForChest()
    {
        Chest currentChest = null;
        for(Chest chest : currentDistrict.getChests())
        {
            if(chest.getPosition().equals(team.getPosition()))
                currentChest = chest;
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

    public void loadNewWorldMap(String newMap) throws IOException
    {
        Save.saveGame(this, currentSave);
        Save.parseSave(this, currentSave);
        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
        currentDistrict = MapParser.parseMap(newMap);
        Save.parseSave(this, currentSave);
    }

    public void loadNewTownMap(String newMap) throws IOException
    {
        Save.saveGame(this, currentSave);
        Save.parseSave(this, currentSave);
        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
        currentDistrict = MapParser.parseMap(newMap);
        Save.parseSave(this, currentSave);
        team.setPosition(new Vector2d(currentDistrict.getWidth() - 1, currentDistrict.getHeight() - 1));
    }

    public void loadNewDungeonMap(String newMap) throws IOException
    {
        Save.saveGame(this, currentSave);
        Save.parseSave(this, currentSave);
        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
        currentDistrict = MapParser.parseMap(newMap);
        Save.parseSave(this, currentSave);
        team.setPosition(new Vector2d(currentDistrict.getWidth() - 1, currentDistrict.getHeight() - 1));
    }

    public void teamHit()
    {
        Group enemies = currentDistrict.findEnemies(team.getPosition());
        if(enemies != null)
        {
            int experience = enemies.damageAll(team.getTeamMembers().get(currentMember).getDamage());
            team.distributeExperience(experience, currentMember);
            currentMember = (currentMember + 1) % 4;
            if(enemies.getEntities().size() == 0)
                currentDistrict.removeGroup(enemies);
        }
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

    public void update()
    {
        if(currentDistrict instanceof World)
            ((World) currentDistrict).updateVisibleTiles(team.getPosition());
    }

    public void takeQuest(Quest quest)
    {
        team.getActiveQuests().add(quest);
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

    public Skill getSkill(String skillName) throws IOException
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
        return team.getTeamMembers().get(0);
    }

    public District getCurrentDistrict()
    {
        return currentDistrict;
    }

    public ArrayList<Class> getClasses()
    {
        return classes;
    }

    public ArrayList<Race> getRaces()
    {
        return races;
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

    public void setClasses(ArrayList<Class> classes)
    {
        this.classes = classes;
    }

    public void setRaces(ArrayList<Race> races)
    {
        this.races = races;
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


}