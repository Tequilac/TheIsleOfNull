package map;

import java.util.Objects;

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
            return MapDirection.West;
        if(x > width - 1)
            return MapDirection.East;
        if(y < 0)
            return MapDirection.North;
        if(y > height - 1)
            return MapDirection.South;
        return null;
    }

    public Vector2d trim(int width, int height)
    {
        return new Vector2d((x+width)%width, (y+height)%height);
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
