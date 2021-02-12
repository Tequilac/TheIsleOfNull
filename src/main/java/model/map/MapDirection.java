package model.map;

public enum MapDirection
{
    NORTH, EAST, SOUTH, WEST;

    public MapDirection getNext()
    {
        return switch(this)
                {
                    case NORTH -> EAST;
                    case EAST -> SOUTH;
                    case SOUTH -> WEST;
                    default -> NORTH;
                };
    }

    public MapDirection getPrevious()
    {
        return switch(this)
                {
                    case NORTH -> WEST;
                    case EAST -> NORTH;
                    case SOUTH -> EAST;
                    default -> SOUTH;
                };
    }

    public Vector2d toVector2d()
    {
        return switch(this)
                {
                    case NORTH -> new Vector2d(0, -1);
                    case EAST -> new Vector2d(1, 0);
                    case SOUTH -> new Vector2d(0, 1);
                    default -> new Vector2d(-1, 0);
                };
    }
}
