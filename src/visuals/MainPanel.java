package visuals;

import visuals.primaryPanels.MapPanel;
import visuals.primaryPanels.PrimaryPanel;
import visuals.secondaryPanels.OptionsPanel;
import visuals.secondaryPanels.SecondaryPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
    private Frame frame;

    private PrimaryPanel primaryPanel;

    private SecondaryPanel leftPanel;

    private SecondaryPanel rightPanel;

    public MainPanel(Frame frame)
    {
        this.frame = frame;

        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(1400, 800));
    }

    public void addPrimaryPanel(PrimaryPanel panel)
    {
        if(primaryPanel != null)
            remove(primaryPanel);
        this.primaryPanel = panel;
        add(panel, BorderLayout.CENTER);
    }

    public void addLeftPanel(SecondaryPanel panel)
    {
        if(leftPanel != null)
            remove(leftPanel);
        this.leftPanel = panel;
        add(panel, BorderLayout.LINE_START);
    }

    public void addRightPanel(SecondaryPanel panel)
    {
        if(rightPanel != null)
            remove(rightPanel);
        this.rightPanel = panel;
        add(panel, BorderLayout.LINE_END);
    }

    public void updateEnemies()
    {
        if(leftPanel instanceof OptionsPanel)
            ((OptionsPanel) leftPanel).setEnemiesName();
    }

    public void updateButtons(boolean onObject, boolean onEntrance)
    {

        if(leftPanel instanceof OptionsPanel)
            ((OptionsPanel) leftPanel).updateButtons(onObject, onEntrance);
    }

    public boolean mapOpened()
    {
        return primaryPanel instanceof MapPanel;
    }

    public PrimaryPanel getPrimaryPanel()
    {
        return primaryPanel;
    }

    public SecondaryPanel getLeftPanel()
    {
        return leftPanel;
    }

    public SecondaryPanel getRightPanel()
    {
        return rightPanel;
    }
}
