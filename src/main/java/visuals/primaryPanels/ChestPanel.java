package visuals.primaryPanels;

import game.Game;
import map.Chest;
import visuals.Frame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChestPanel extends PrimaryPanel
{
    private Chest chest;

    private JButton gold;

    private JLabel[] items;

    private JButton closeButton;

    public ChestPanel(Game game, Chest chest, Frame frame)
    {
        super(game, frame);

        this.chest = chest;

        closeButton = new JButton(new ImageIcon("src/main/resources/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            game.doAction();
            frame.updateButtons(true, false);
        });
        closeButton.setBounds(750, 10, 40, 40);
        add(closeButton);

        setChestContent();
    }

    public void setChestContent()
    {
        if(gold != null)
            remove(gold);
        this.gold = new JButton("Gold: " + chest.getGold());
        gold.setBounds(0, 0, 250, 20);
        add(gold);
        gold.addActionListener(actionEvent ->
        {
            frame.getGame().transferGold();
            setChestContent();
        });

        if(items != null)
        {
            for (JLabel item : items)
            {
                if (item != null)
                    remove(item);
            }
        }
        this.items = new JLabel[chest.getItems().size()];


        for (int i = 0; i < chest.getItems().size(); i++)
        {
            items[i] = null;
            items[i] = chest.getItems().get(i).getLabel();
            items[i].setBounds(0, 20 + i*50, 40, 40);
            add(items[i]);
            int finalI = i;
            items[i].addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent mouseEvent)
                {
                    game.transferItem(chest.getItems().get(finalI));
                    remove(items[finalI]);
                    setChestContent();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent)
                {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent)
                {

                }
            });
        }
        updateUI();
        frame.requestFocus();
    }
}
