package inhabitants;

import map.Vector2d;

public class Inhabitant
{
    protected String name;

    protected String description;

    protected Vector2d position;

    public Inhabitant(String name, String description, Vector2d position)
    {
        this.name = name;
        this.description = description;
        this.position = position;
    }
}
