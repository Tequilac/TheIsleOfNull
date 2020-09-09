package entities;

import map.*;
import map.districts.District;
import map.districts.Town;
import map.districts.World;

import java.util.ArrayList;

public class Group
{
    protected Vector2d position;

    protected MapDirection mapDirection;

    protected ArrayList<Entity> entities;

    public Group(Vector2d position, MapDirection mapDirection, ArrayList<Entity> entities)
    {
        this.position = position;
        this.mapDirection = mapDirection;
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

    public int damageAll(int value)
    {
        for(Entity entity : entities)
        {
            entity.damage(value);
        }
        return checkForDead();
    }

    public boolean moveFront(District district)
    {
        if(district instanceof World && ((World) district).canMove(position.add(mapDirection.toVector2d())))
        {
            position = position.add(mapDirection.toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        if(district instanceof Town && ((Town) district).canMove(position.add(mapDirection.toVector2d())))
        {
            position = position.add(mapDirection.toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        return false;
    }

    public boolean moveBack(District district)
    {
        if(district instanceof World && ((World) district).canMove(position.subtract(mapDirection.toVector2d())))
        {
            position = position.subtract(mapDirection.toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        if(district instanceof Town && ((Town) district).canMove(position.subtract(mapDirection.toVector2d())))
        {
            position = position.subtract(mapDirection.toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        return false;
    }

    public boolean moveLeft(District district)
    {
        if(district instanceof World && ((World) district).canMove(position.subtract(mapDirection.getNext().toVector2d())))
        {
            position = position.subtract(mapDirection.getNext().toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        if(district instanceof Town && ((Town) district).canMove(position.subtract(mapDirection.getNext().toVector2d())))
        {
            position = position.subtract(mapDirection.getNext().toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        return false;
    }

    public boolean moveRight(District district)
    {
        if(district instanceof World && ((World) district).canMove(position.subtract(mapDirection.getPrevious().toVector2d())))
        {
            position = position.subtract(mapDirection.getPrevious().toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        if(district instanceof Town && ((Town) district).canMove(position.subtract(mapDirection.getPrevious().toVector2d())))
        {
            position = position.subtract(mapDirection.getPrevious().toVector2d()).trim(district.getWidth(), district.getHeight());
            return true;
        }
        return false;
    }

    public boolean move(MoveDirection moveDirection, District district)
    {
        boolean moved = false;
        switch (moveDirection)
        {
            case Left: moved = moveLeft(district);
            break;
            case Right: moved = moveRight(district);
            break;
            case Front: moved = moveFront(district);
            break;
            case Back: moved = moveBack(district);
            break;
            case TurnLeft: mapDirection = mapDirection.getPrevious();
            moved = true;
            break;
            default: mapDirection = mapDirection.getNext();
            moved = true;
        }
        return moved;
    }

    public MapDirection changeMap(MoveDirection moveDirection, District district)
    {
        MapDirection newPosition = null;
        switch (moveDirection)
        {
            case Left: newPosition = position.subtract(mapDirection.getNext().toVector2d()).exceeds(district.getWidth(), district.getHeight());
                break;
            case Right: newPosition = position.subtract(mapDirection.getPrevious().toVector2d()).exceeds(district.getWidth(), district.getHeight());
                break;
            case Front: newPosition = position.add(mapDirection.toVector2d()).exceeds(district.getWidth(), district.getHeight());
                break;
            case Back: newPosition = position.subtract(mapDirection.toVector2d()).exceeds(district.getWidth(), district.getHeight());
                break;
        }
        return newPosition;
    }

    public void makeDecision(Vector2d teamPosition, District district)
    {
        Vector2d relativePosition = isNear(teamPosition);
        if(!(relativePosition).equals(new Vector2d(-1, -1)))
        {
            if(relativePosition.getY() < 0)
            {
                if(mapDirection == MapDirection.South)
                    move(MoveDirection.Front, district);
                else
                    move(MoveDirection.TurnLeft, district);
            }
            else if(relativePosition.getY() > 0)
            {
                if(mapDirection == MapDirection.North)
                    move(MoveDirection.Front, district);
                else
                    move(MoveDirection.TurnLeft, district);
            }
            else if(relativePosition.getX() < 0)
            {
                if(mapDirection == MapDirection.East)
                    move(MoveDirection.Front, district);
                else
                    move(MoveDirection.TurnLeft, district);
            }
            else if(relativePosition.getX() > 0)
            {
                if(mapDirection == MapDirection.West)
                    move(MoveDirection.Front, district);
                else
                    move(MoveDirection.TurnLeft, district);
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
        return new Vector2d(-1, -1);
    }

    public int checkForDead()
    {
        int experience = 0;
        for (int i = entities.size() - 1; i > -1; i--)
        {
            if(entities.get(i).isDead())
            {
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
