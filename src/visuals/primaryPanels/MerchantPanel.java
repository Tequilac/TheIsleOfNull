package visuals.primaryPanels;

import inhabitants.Merchant;
import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MerchantPanel extends PrimaryPanel
{
    private Merchant merchant;

    private JLabel gold;

    private JLabel[] items;

    private JButton closeButton;

    public MerchantPanel(Game game, Frame frame, Merchant merchant)
    {
        super(game, frame);

        setLayout(null);
        this.merchant = merchant;

        closeButton = new JButton(new ImageIcon("res/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            game.doAction();
            frame.updateButtons(true, false);
        });
        closeButton.setBounds(750, 10, 40, 40);
        add(closeButton);

        drawOfferedItem();
    }

    public void drawOfferedItem()
    {
        if(gold != null)
            remove(gold);
        this.gold = new JLabel("Your gold: " + game.getTeam().getGold());
        gold.setBounds(0, 0, 250, 20);
        add(gold);

        if(items != null)
        {
            for (JLabel item : items)
            {
                if (item != null)
                    remove(item);
            }
        }
        this.items = new JLabel[merchant.getOfferedItems().size()];


        for (int i = 0; i < merchant.getOfferedItems().size(); i++)
        {
            items[i] = null;
            items[i] = merchant.getOfferedItems().get(i).getLabel();
            items[i].setBounds(0, 20 + i*50, 40, 40);
            add(items[i]);
            int finalI = i;
            items[i].addMouseListener(new MouseListener()
            {
                @Override
                public void mouseClicked(MouseEvent mouseEvent)
                {
                    if(merchant.getOfferedItems().get(finalI).canBeBought(game.getTeam().getGold()))
                    {
                        game.getTeam().removeGold(merchant.getOfferedItems().get(finalI).getCost());
                        game.buyItem(merchant.getOfferedItems().get(finalI));
                        remove(items[finalI]);
                        drawOfferedItem();
                    }
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
