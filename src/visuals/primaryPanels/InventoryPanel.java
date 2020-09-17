package visuals.primaryPanels;

import entities.Team;
import items.ItemParser;
import main.Game;
import quests.QuestGiver;
import visuals.EquipmentComponent;
import visuals.Frame;
import visuals.TransferableItem;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InventoryPanel extends PrimaryPanel implements DragGestureListener
{
    private final Team team;

    private JLabel[] items;

    private EquipmentComponent equipmentComponent;

    public InventoryPanel(Game game, Frame frame)
    {
        super(game, frame);

        setLayout(null);

        this.team = game.getTeam();

        this.equipmentComponent = new EquipmentComponent(this, team, game.getCurrentCharacter());
        equipmentComponent.setBounds(400, 0, 400, 800);
        add(equipmentComponent);

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
