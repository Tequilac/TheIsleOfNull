package model.map;

public enum MapDirection
{
    North, East, South, West;

    public MapDirection getNext()
    {
        switch (this)
        {
            case North: return East;
            case East: return South;
            case South: return West;
            default: return North;
        }
    }

    public MapDirection getPrevious()
    {
        switch (this)
        {
            case North: return West;
            case East: return North;
            case South: return East;
            default: return South;
        }
    }

    public Vector2d toVector2d()
    {
        switch (this)
        {
            case North: return new Vector2d(0, -1);
            case East: return new Vector2d(1, 0);
            case South: return new Vector2d(0, 1);
            default: return new Vector2d(-1, 0);
        }
    }
}
