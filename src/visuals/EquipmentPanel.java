package visuals;

import items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;

public class EquipmentPanel extends JPanel
{
    private JLabel itemIcon;

    public EquipmentPanel()
    {
        super();
        setBackground(Color.CYAN);
    }

    public void addItem(Item item)
    {
        itemIcon = item.getLabel();
        itemIcon.setBounds(5, 5, 40, 40);
        itemIcon.setToolTipText("<html>" + item.toString() + "</html>");


        add(itemIcon);
        updateUI();
    }
}
