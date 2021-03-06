package model.entities;

import model.map.*;
import model.map.districts.District;

import java.util.ArrayList;

public class Group
{
    protected Vector2d position;

    protected MapDirection mapDirection;

    protected ArrayList<Entity> entities;

    public Group(Vector2d position, MapDirection mapDirection, ArrayList<Entity> entities)
    {
        this(position, mapDirection);
        this.entities = entities;
    }

    public Group(Vector2d position, MapDirection mapDirection)
    {
        this.position = position;
        this.mapDirection = mapDirection;
    }

    public Vector2d getPosition()
    {
        return position;
    }

    public MapDirection getMapDirection()
    {
        return mapDirection;
    }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

    public void damageOne(int value, int index)
    {
        entities.get(index).damage(value);
    }

    public int damageAll(Team team, int value)
    {
        for(Entity entity : entities)
        {
            entity.damage(value);
        }
        return checkForDead(team);
    }

    private boolean moveSomewhere(MoveDirection moveDirection, District district)
    {
        Vector2d newPosition = switch(moveDirection)
                {
                    case FRONT -> position.add(mapDirection.toVector2d());
                    case BACK -> position.subtract(mapDirection.toVector2d());
                    case LEFT -> position.subtract(mapDirection.getNext().toVector2d());
                    case RIGHT -> position.subtract(mapDirection.getPrevious().toVector2d());
                    default -> throw new IllegalStateException("Unexpected value: " + moveDirection);
                };
        if(district.canMove(newPosition))
        {
            position = newPosition.trim(district);
            return true;
        }
        return false;
    }

    public boolean move(MoveDirection moveDirection, District district)
    {
        boolean moved;
        switch(moveDirection)
        {
            case TURN_LEFT -> {
                mapDirection = mapDirection.getPrevious();
                moved = true;
            }
            case TURN_RIGHT -> {
                mapDirection = mapDirection.getNext();
                moved = true;
            }
            default -> moved = moveSomewhere(moveDirection, district);
        }
        return moved;
    }

    public MapDirection changeMap(MoveDirection moveDirection, District district)
    {
        return switch(moveDirection)
                {
                    case LEFT -> position.subtract(mapDirection.getNext().toVector2d()).exceeds(district.getWidth(), district.getHeight());
                    case RIGHT -> position.subtract(mapDirection.getPrevious().toVector2d()).exceeds(district.getWidth(), district.getHeight());
                    case FRONT -> position.add(mapDirection.toVector2d()).exceeds(district.getWidth(), district.getHeight());
                    case BACK -> position.subtract(mapDirection.toVector2d()).exceeds(district.getWidth(), district.getHeight());
                    default -> null;
                };
    }

    public void makeDecision(Vector2d teamPosition, District district)
    {
        Vector2d relativePosition = isNear(teamPosition);
        if(!(relativePosition).equals(new Vector2d(-10, -10)))
        {
            if(relativePosition.getY() < 0)
            {
                if(mapDirection == MapDirection.SOUTH)
                    move(MoveDirection.FRONT, district);
                else
                {
                    if(relativePosition.getX() >= 0)
                        move(MoveDirection.TURN_LEFT, district);
                    else
                        move(MoveDirection.TURN_RIGHT, district);
                }
            }
            else if(relativePosition.getY() > 0)
            {
                if(mapDirection == MapDirection.NORTH)
                    move(MoveDirection.FRONT, district);
                else
                {
                    if(relativePosition.getX() <= 0)
                        move(MoveDirection.TURN_LEFT, district);
                    else
                        move(MoveDirection.TURN_RIGHT, district);
                }
            }
            else if(relativePosition.getX() < 0)
            {
                if(mapDirection == MapDirection.EAST)
                    move(MoveDirection.FRONT, district);
                else
                {
                    if(relativePosition.getY() >= 0)
                        move(MoveDirection.TURN_LEFT, district);
                    else
                        move(MoveDirection.TURN_RIGHT, district);
                }
            }
            else if(relativePosition.getX() > 0)
            {
                if(mapDirection == MapDirection.WEST)
                    move(MoveDirection.FRONT, district);
                else
                {
                    if(relativePosition.getY() <= 0)
                        move(MoveDirection.TURN_LEFT, district);
                    else
                        move(MoveDirection.TURN_RIGHT, district);
                }
            }
        }
    }

    public Vector2d isNear(Vector2d teamPosition)
    {
        for (int i = -5; i <= 5; i++)
        {
            for (int j = -5; j <= 5; j++)
            {
                if(teamPosition.add(new Vector2d(i, j)).equals(position))
                    return new Vector2d(i, j);
            }
        }
        return new Vector2d(-10, -10);
    }

    public int checkForDead(Team team)
    {
        int experience = 0;
        for (int i = entities.size() - 1; i > -1; i--)
        {
            if(entities.get(i).isDead())
            {
                team.updateKillQuests((Enemy) entities.get(i));
                experience += kill(i);
            }
        }
        return experience;
    }

    public int kill(int index)
    {
        Entity killed = entities.get(index);
        entities.remove(killed);
        return killed.getExperience();
    }

    public void setPosition(Vector2d position)
    {
        this.position = position;
    }
}
