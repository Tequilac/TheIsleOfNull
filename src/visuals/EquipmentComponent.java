package visuals;

import items.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class EquipmentComponent extends JComponent
{
    private EquipmentPanel head;

    private EquipmentPanel torso;

    private EquipmentPanel leftHand;

    private EquipmentPanel rightHand;

    public EquipmentComponent()
    {
        super();
        setPreferredSize(new Dimension(400, 800));
        repaint();
        loadEquipmentPanels();
    }

    public void loadEquipmentPanels()
    {
        MyDropTargetListener mtl;

        head = new EquipmentPanel();
        head.setBounds(155, 50, 50, 50);
        mtl = new MyDropTargetListener(head);
        add(head);

        torso = new EquipmentPanel();
        torso.setBounds(155, 350, 50, 50);
        mtl = new MyDropTargetListener(torso);
        add(torso);

        leftHand = new EquipmentPanel();
        leftHand.setBounds(30, 440, 50, 50);
        mtl = new MyDropTargetListener(leftHand);
        add(leftHand);

        rightHand = new EquipmentPanel();
        rightHand.setBounds(300, 410, 50, 50);
        mtl = new MyDropTargetListener(rightHand);
        add(rightHand);
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

                if (event.isDataFlavorSupported(TransferableItem.itemFlavor))
                {

                    event.acceptDrop(DnDConstants.ACTION_COPY);
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
