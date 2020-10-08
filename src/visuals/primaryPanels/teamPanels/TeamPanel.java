package visuals.primaryPanels.teamPanels;

import main.Game;
import visuals.Frame;
import visuals.primaryPanels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TeamPanel extends PrimaryPanel
{
    private JTabbedPane pane;

    private int currentTab;

    public TeamPanel(Game game, Frame frame, Integer currentTab)
    {
        super(game, frame);

        setLayout(new FlowLayout());

        if(currentTab != null)
            this.currentTab = currentTab;

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

        pane.addTab("Spells", new KnownSpellsPanel(game, frame));
        pane.setMnemonicAt(4, KeyEvent.VK_5);

        pane.setSelectedIndex(currentTab);

        add(pane);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    public int getCurrentTab()
    {
        return pane.getSelectedIndex();
    }
}
