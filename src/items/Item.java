package items;

import javax.swing.*;

public abstract class Item
{
    protected final int cost;

    protected final String name;

    protected final String type;

    public Item(int cost, String name, String type)
    {
        this.cost = cost;
        this.name = name;
        this.type = type;
    }

    public abstract int getValue();

    public int getCost()
    {
        return cost;
    }

    public String getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public JLabel getLabel()
    {
        String path = "res/graphics/items/" + type.toLowerCase() + ".png";
        JLabel label = new JLabel(new ImageIcon(path));
        label.setToolTipText("<html>" + toString() + "</html>");
        return label;
    }

    @Override
    public String toString()
    {
        return  name + "<br>" +
                "Cost: " + cost + "<br>" +
                "Type: " + type;
    }
}
