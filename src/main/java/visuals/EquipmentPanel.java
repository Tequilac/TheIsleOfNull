package visuals;

import model.equipment.Equipment;
import model.items.Item;
import model.items.ItemParser;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.io.IOException;

public class EquipmentPanel extends JPanel implements DragGestureListener
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
        DragSource ds = new DragSource();

        itemIcon = item.getLabel();
        itemIcon.setBounds(5, 5, 40, 40);
        itemIcon.setToolTipText("<html>" + item.toString() + "</html>");

        ds.createDefaultDragGestureRecognizer(itemIcon, DnDConstants.ACTION_COPY, this);

        add(itemIcon);
        updateUI();
    }

    public void clear()
    {
        equipment.clear();
        remove(itemIcon);
        updateUI();
    }

    public Equipment getEquipment()
    {
        return equipment;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event)
    {
        var cursor = Cursor.getDefaultCursor();
        JLabel label = (JLabel) event.getComponent();
        String text = label.getToolTipText();
        text = text.substring(6, text.indexOf("<br>"));

        if(event.getDragAction() == DnDConstants.ACTION_COPY)
        {
            cursor = DragSource.DefaultCopyDrop;
        }

        try
        {
            event.startDrag(cursor, new TransferableItem(ItemParser.parseItem(text)));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
