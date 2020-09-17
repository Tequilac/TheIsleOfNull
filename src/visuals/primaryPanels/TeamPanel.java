package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TeamPanel extends PrimaryPanel
{
    private JTabbedPane pane;

    public TeamPanel(Game game, Frame frame)
    {
        super(game, frame);

        createPane();
    }

    public void createPane()
    {

        pane = new JTabbedPane();

        pane.setPreferredSize(new Dimension(800, 800));


        pane.addTab("Statistics", new TeamStatisticsPanel(game, frame));
        pane.setMnemonicAt(0, KeyEvent.VK_1);

        pane.addTab("Skills", new SkillsPanel(game, frame));
        pane.setMnemonicAt(1, KeyEvent.VK_2);

        pane.addTab("Inventory", new InventoryPanel(game, frame));
        pane.setMnemonicAt(2, KeyEvent.VK_3);

        pane.addTab("Quests", new ActiveQuestsPanel(game, frame));
        pane.setMnemonicAt(3, KeyEvent.VK_4);

        add(pane);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
}
