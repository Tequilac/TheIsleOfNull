package visuals.primaryPanels.teamPanels;

import entities.Team;
import items.Item;
import items.ItemParser;
import main.Game;
import quests.QuestGiver;
import visuals.*;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InventoryPanel extends PrimaryPanel
{
    private TeamInventoryPanel teamInventoryPanel;

    private EquipmentComponent equipmentComponent;

    public InventoryPanel(Game game, Frame frame, TeamPanel teamPanel)
    {
        super(game, frame);

        MyDropTargetListener mtl;
        this.teamInventoryPanel = new TeamInventoryPanel(game, teamPanel, game.getTeam());
        mtl = new MyDropTargetListener(teamInventoryPanel);
        teamInventoryPanel.setBounds(0, 0, 400, 800);
        add(teamInventoryPanel);

        this.equipmentComponent = new EquipmentComponent(this, game.getTeam(), game.getCurrentCharacter());
        equipmentComponent.setBounds(400, 0, 400, 800);
        add(equipmentComponent);
    }

    public void drawItems()
    {
        teamInventoryPanel.drawItems();
    }

    private class MyDropTargetListener extends DropTargetAdapter
    {

        private final DropTarget dropTarget;

        private final TeamInventoryPanel panel;

        public MyDropTargetListener(TeamInventoryPanel panel)
        {
            this.panel = panel;

            dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this, true, null);
        }


        public void drop(DropTargetDropEvent event)
        {

            try
            {

                var tr = event.getTransferable();
                var item = (Item) tr.getTransferData(TransferableItem.itemFlavor);

                if (event.isDataFlavorSupported(TransferableItem.itemFlavor) && panel.getTeam().addItemToInventory(item))
                {
                    event.acceptDrop(DnDConstants.ACTION_COPY);

                    equipmentComponent.removeItem(item);
                    drawItems();

                    equipmentComponent.repaint();

                    event.dropComplete(true);
                    return;
                }

                event.rejectDrop();
            }
            catch (Exception e)
            {

                e.printStackTrace();
                event.rejectDrop();
            }
        }
    }
}
