package visuals;

import entities.Team;
import items.ItemParser;
import visuals.primaryPanels.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.io.IOException;

public class TeamInventoryPanel extends JPanel implements DragGestureListener
{
    private InventoryPanel inventoryPanel;

    private Team team;

    private JLabel[] items;

    public TeamInventoryPanel(InventoryPanel inventoryPanel, Team team)
    {
        super();
        this.inventoryPanel = inventoryPanel;
        this.team = team;

        drawItems();
    }

    public void drawItems()
    {
        DragSource ds = new DragSource();

        if(this.items != null)
        {
            for(int i = 0; i < items.length; i++)
            {
                remove(items[i]);
            }
        }
        updateUI();

        this.items = new JLabel[team.getItemsInInventory().size()];
        for (int i = 0; i < team.getItemsInInventory().size(); i++)
        {
            items[i] = team.getItemsInInventory().get(i).getLabel();
            items[i].setBounds(0, 20 + i*40, 40, 40);
            items[i].setToolTipText("<html>" + team.getItemsInInventory().get(i).toString() + "</html>");

            ds.createDefaultDragGestureRecognizer(items[i], DnDConstants.ACTION_COPY, this);

            add(items[i]);
        }
    }

    public Team getTeam()
    {
        return team;
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
