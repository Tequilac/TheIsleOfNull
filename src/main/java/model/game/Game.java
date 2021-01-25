package model.game;

import model.classes.ClassesRepository;
import com.google.inject.Singleton;
import model.entities.Character;
import model.classes.Class;
import model.entities.*;
import model.inhabitants.Inhabitant;
import model.inhabitants.Merchant;
import model.items.Item;
import model.magic.DamageSpell;
import model.map.*;
import model.map.districts.*;
import model.quests.Quest;
import model.quests.QuestGiver;
import model.quests.QuestStatus;
import model.races.*;
import model.saves.Save;
import model.skills.Skill;
import model.skills.SkillsRepository;
import visuals.Frame;

import com.google.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Singleton
public class Game
{
    private File currentSave;

    private Team team;

    private int currentMember;

    private Character currentCharacter;

    private Group chosenEnemies;

    private final MapSystem mapSystem;

    private final RacesRepository racesRepository;

    private final EnemyRacesRepository enemyRacesRepository;

    private final ClassesRepository classesRepository;

    private final SkillsRepository skillsRepository;

    @Inject
    public Game(MapSystem mapSystem,
                RacesRepository racesRepository, EnemyRacesRepository enemyRacesRepository,
                ClassesRepository classesRepository, SkillsRepository skillsRepository) throws IOException
    {
        this.mapSystem = mapSystem;
        this.racesRepository = racesRepository;
        this.enemyRacesRepository = enemyRacesRepository;
        this.classesRepository = classesRepository;
        this.skillsRepository = skillsRepository;
        loadClasses(new File("src/main/resources/classes"));
        loadRaces(new File("src/main/resources/races"));
        createStartingCharacters();
        currentMember = 0;
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

    public void createStartingCharacters() throws IOException
    {
        ArrayList<Character> characters = new ArrayList<>(4);
        for (int i = 0; i < 4; i++)
        {
            Character character = new Character("Choose name", racesRepository.getRace("Human"), 0, 0, classesRepository.getClass("Archer"));
            characters.add(character);
        }
        team = new Team(new Vector2d(32, 34), MapDirection.North, characters);
    }

    public void loadSaveGame(File filename) throws IOException
    {
        this.currentSave = filename;
        Save.parseSave(this, filename);
        this.currentCharacter = team.getTeamMembers().get(0);
    }

    public void saveGame() throws IOException
    {
        Save.saveGame(this, currentSave);
    }

    public void createTeam()
    {
        mapSystem.updateMapTiles(team.getPosition());
        currentCharacter = team.getTeamMembers().get(0);
    }

    public void tick(MoveDirection direction) throws IOException
    {
        mapSystem.tryToChangeMap(team, direction);
        if(mapSystem.move(team, direction))
        {
            mapSystem.monstersMakeDecision(team.getPosition());
        }

//        checkForMonsters();
//
//        checkForObjects();
//
//        checkForEntrances();
    }

    public void teamHit()
    {
//        if(currentDistrict instanceof AbstractHiddenDistrict district)
//        {
//            Group enemies = district.findEnemies(team.getPosition());
//            if(enemies != null)
//            {
//                int experience = enemies.damageAll(team, team.getTeamMembers().get(currentMember).getDamage());
//                team.distributeExperience(experience, currentMember);
//                currentMember = (currentMember + 1) % 4;
//                if(enemies.getEntities().size() == 0)
//                    district.removeGroup(enemies);
//            }
//            retaliate();
//        }
    }

    public void update()
    {
        mapSystem.updateMapTiles(team.getPosition());
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
//        ((Merchant)metInhabitant).getOfferedItems().remove(item);
//        team.addItemToInventory(item);
    }

    public void transferItem(Item item)
    {
//        openedChest.getItems().remove(item);
//        team.addItemToInventory(item);
    }

    public void transferGold()
    {
//        team.addGold(openedChest.getGold());
//        openedChest.setGold(0);
    }

    public void castDamageSpell(DamageSpell spell)
    {
//        if(currentDistrict instanceof AbstractHiddenDistrict district)
//        {
//            Group enemies = district.findEnemies(team.getPosition());
//            if(enemies != null)
//            {
//                int experience = enemies.damageAll(team, spell.getValue());
//                team.distributeExperience(experience, currentMember);
//                currentMember = (currentMember + 1) % 4;
//                if(enemies.getEntities().size() == 0)
//                    district.removeGroup(enemies);
//                ((MagicCharacter)currentCharacter).removeMana(spell.getManaCost());
//            }
//        }
    }

    public void retaliate()
    {
//        for (Group enemyGroup : ((AbstractHiddenDistrict) currentDistrict).getEnemies())
//        {
//            if(enemyGroup.getPosition().equals(team.getPosition()))
//            {
//                for(Entity enemy : enemyGroup.getEntities())
//                {
//                    team.getTeamMembers().get(currentMember).damage(((Enemy) enemy).getDamage());
//                    currentMember = (currentMember+1)%4;
//                }
//            }
//        }
    }

    public Race getEnemyRace(String raceName) throws FileNotFoundException
    {
        return enemyRacesRepository.getEnemyRace(raceName);
    }

    public Race getRace(String raceName) throws FileNotFoundException
    {
        return racesRepository.getRace(raceName);
    }

    public Class getClass(String className) throws IOException
    {
        return classesRepository.getClass(className);
    }

    public Skill getSkill(String skillName)
    {
        return skillsRepository.getSkill(skillName);
    }

    public Character getCurrentCharacter()
    {
        return currentCharacter;
    }

    public Map<String, Class> getClasses()
    {
        return classesRepository.getAllClasses();
    }

    public Map<String, Race> getRaces()
    {
        return racesRepository.getAllRaces();
    }

    public ArrayList<Character> getCharacters()
    {
        return team.getTeamMembers();
    }

    public Team getTeam()
    {
        return team;
    }

    public String getTeamInfo()
    {
        return team.getTeamInfo();
    }

    public Group getChosenEnemies()
    {
        return chosenEnemies;
    }

    public void setCurrentSave(File currentSave)
    {
        this.currentSave = currentSave;
    }

    public void setTeam(Team team)
    {
        this.team = team;
    }

    public void setTeamInfo(String teamInfo)
    {
        this.team.setTeamInfo(teamInfo);
    }

    public void setCurrentMember(int currentMember)
    {
        this.currentMember = currentMember;
    }

    public void setCurrentCharacter(Character currentCharacter)
    {
        this.currentCharacter = currentCharacter;
    }
}