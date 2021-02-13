package model.game;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.entities.Group;
import model.entities.Team;
import model.inhabitants.Inhabitant;
import model.map.*;
import model.map.districts.*;
import model.quests.QuestGiver;

import java.io.IOException;

@Singleton
public class MapSystem
{
    private Team team;

    private District currentDistrict;

    private Chest openedChest;

    private QuestGiver metQuestGiver;

    private Inhabitant metInhabitant;

    private boolean onObject;

    private boolean onEntrance;

    @Inject
    public MapSystem() throws IOException
    {
        currentDistrict = MapParser.parseMap("W11");
    }

    public void updateMapTiles(Vector2d position)
    {
        ((World) currentDistrict).updateVisibleTiles(position);
    }

    public boolean move(Team team, MoveDirection direction)
    {
        return team.move(direction, currentDistrict);
    }

    public void tryToChangeMap(Team team, MoveDirection direction)
    {
        String newMap = changeMap(team, direction);
        if(newMap != null)
        {
            loadNewWorldMap(newMap);
        }
    }

    public String changeMap(Team team, MoveDirection direction)
    {
        MapDirection changeDirection = team.changeMap(direction, currentDistrict);
        if(changeDirection == null)
            return null;
        if(currentDistrict instanceof World)
        {
            return switch(changeDirection)
                    {
                        case NORTH -> "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2))) + 1);
                        case SOUTH -> "W" + currentDistrict.getName().charAt(1) + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(2))) - 1);
                        case WEST -> "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1))) + 1) + currentDistrict.getName().charAt(2);
                        case EAST -> "W" + (Integer.parseInt(String.valueOf(currentDistrict.getName().charAt(1))) - 1) + currentDistrict.getName().charAt(2);
                    };
        }
        else
        {
            return switch(changeDirection)
                    {
                        case NORTH, SOUTH, WEST, EAST -> "W" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2);
                    };
        }
    }

    public void monstersMakeDecision(Vector2d position)
    {
        if(currentDistrict instanceof AbstractHiddenDistrict district)
        {
            district.monstersMakeDecisions(position);
        }
    }

    public void checkForMonsters()
    {
        if(currentDistrict instanceof AbstractHiddenDistrict district)
        {
            MapDirection direction = team.getMapDirection();

            Vector2d position = team.getPosition();
            Vector2d left = position.add(direction.getPrevious().toVector2d());
            Vector2d right = position.add(direction.getNext().toVector2d());

            for (int i = 0; i < 5; i++)
            {
                for(Group group : district.getEnemies())
                {
                    if(group.getPosition().equals(left) ||
                            group.getPosition().equals(position) ||
                            group.getPosition().equals(right))
                    {
                        return;
                    }
                }
                left = left.add(direction.toVector2d());
                position = position.add(direction.toVector2d());
                right = right.add(direction.toVector2d());
            }
        }
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
            if(((World) currentDistrict).isTownEntrance(team.getPosition()) || ((World) currentDistrict).isDungeonEntrance(team.getPosition()))
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
    }

    public void checkForTownEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isTownEntrance(team.getPosition()))
        {
            loadNewInnerMap("T" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
        }
    }

    public void checkForDungeonEnter() throws IOException
    {
        if(currentDistrict instanceof World && ((World) currentDistrict).isDungeonEntrance(team.getPosition()))
        {
            loadNewInnerMap("D" + currentDistrict.getName().charAt(1) + currentDistrict.getName().charAt(2));
        }
    }

    public void doAction()
    {
        if(openedChest != null)
        {
            openedChest = null;
        }
        else
        {
            checkForChest();
//            if(openedChest != null)
//            {
//            }
        }
        if(metQuestGiver != null)
        {
            metQuestGiver = null;
        }
        else
        {
            checkForQuestGiver();
//            if(metQuestGiver != null)
//            {
//            }
        }
        if(metInhabitant != null)
        {
            metInhabitant = null;
        }
        else
        {
            checkForInhabitant();
//            if(metInhabitant != null)
//            {
//            }
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

    public void loadNewWorldMap(String newMap)
    {
        boolean isTown = (currentDistrict instanceof Town);
        boolean isDungeon = (currentDistrict instanceof Dungeon);
//        Save.saveGame(this, currentSave);
//        Save.parseSave(this, currentSave);
//        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
//        currentDistrict = MapParser.parseMap(newMap);
//        Save.parseSave(this, currentSave);
//        if(isTown)
//            team.setPosition(((World)currentDistrict).getTownEntrance());
//        if(isDungeon)
//            team.setPosition(((World)currentDistrict).getDungeonEntrance());
    }

    private void loadNewInnerMap(String newMap)
    {
//        Save.saveGame(this, currentSave);
//        Save.parseSave(this, currentSave);
//        Save.setCurrentDistrict(this, currentSave, newMap, team.getPosition(), team.getMapDirection());
//        currentDistrict = MapParser.parseMap(newMap);
//        Save.parseSave(this, currentSave);
        team.setPosition(new Vector2d(currentDistrict.getWidth() - 1, currentDistrict.getHeight() - 1));
    }

    public District getCurrentDistrict()
    {
        return currentDistrict;
    }
}
