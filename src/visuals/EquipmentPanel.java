package visuals;

import equipment.Equipment;
import items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;

public class EquipmentPanel extends JPanel
{
    private JLabel itemIcon;

    private Equipment equipment;

    public EquipmentPanel(Equipment equipment)
    {
        super();
        setBackground(Color.CYAN);
        this.equipment = equipment;
        if(equipment.getItem() != null)
            addItem(equipment.getItem());
    }

    public void addItem(Item item)
    {
        itemIcon = item.getLabel();
        itemIcon.setBounds(5, 5, 40, 40);
        itemIcon.setToolTipText("<html>" + item.toString() + "</html>");


        add(itemIcon);
        updateUI();
    }

    public Equipment getEquipment()
    {
        return equipment;
    }
}
