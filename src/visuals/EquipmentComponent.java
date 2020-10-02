package visuals;

import entities.Character;
import entities.Team;
import items.Item;
import visuals.primaryPanels.teamPanels.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class EquipmentComponent extends JComponent
{
    private final InventoryPanel inventoryPanel;

    private final Team team;

    private final Character character;

    private EquipmentPanel head;

    private EquipmentPanel torso;

    private EquipmentPanel leftHand;

    private EquipmentPanel rightHand;

    public EquipmentComponent(InventoryPanel inventoryPanel, Team team, Character character)
    {
        super();
        setPreferredSize(new Dimension(400, 800));

        this.inventoryPanel = inventoryPanel;
        this.team = team;
        this.character = character;

        repaint();
        loadEquipmentPanels();
    }

    public void loadEquipmentPanels()
    {
        MyDropTargetListener mtl;

        head = new EquipmentPanel(character.getHead());
        head.setBounds(155, 50, 50, 50);
        mtl = new MyDropTargetListener(head);
        add(head);

        torso = new EquipmentPanel(character.getTorso());
        torso.setBounds(155, 350, 50, 50);
        mtl = new MyDropTargetListener(torso);
        add(torso);

        leftHand = new EquipmentPanel(character.getLeftHand());
        leftHand.setBounds(30, 440, 50, 50);
        mtl = new MyDropTargetListener(leftHand);
        add(leftHand);

        rightHand = new EquipmentPanel(character.getRightHand());
        rightHand.setBounds(300, 410, 50, 50);
        mtl = new MyDropTargetListener(rightHand);
        add(rightHand);
    }

    public void removeItem(Item item)
    {
        EquipmentPanel[] equipmentPanels = {head, leftHand, rightHand, torso};
        for(EquipmentPanel panel : equipmentPanels)
        {
            if(panel.getEquipment().getItem() != null && panel.getEquipment().getItem().getName().equals(item.getName()))
            {
                panel.clear();
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("res/graphics/character.png").getImage(), 0, 0, this);
    }

    private class MyDropTargetListener extends DropTargetAdapter
    {

        private final DropTarget dropTarget;

        private final EquipmentPanel panel;

        public MyDropTargetListener(EquipmentPanel panel)
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

                if (event.isDataFlavorSupported(TransferableItem.itemFlavor) && panel.getEquipment().addItem(item))
                {
                    event.acceptDrop(DnDConstants.ACTION_COPY);

                    team.removeItemFromInventory(item);
                    inventoryPanel.drawItems();

                    this.panel.addItem(item);

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
