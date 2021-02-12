package model.map;

import model.map.districts.District;

public class Vector2d
{
    private final int x;

    private final int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other)
    {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public MapDirection exceeds(int width, int height)
    {
        if(x < 0)
            return MapDirection.WEST;
        if(x > width - 1)
            return MapDirection.EAST;
        if(y < 0)
            return MapDirection.NORTH;
        if(y > height - 1)
            return MapDirection.SOUTH;
        return null;
    }

    public Vector2d trim(District district)
    {
        return new Vector2d((x + district.getWidth()) % district.getWidth(), (y + district.getHeight()) % district.getHeight());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
