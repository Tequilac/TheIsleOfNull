package visuals.primaryPanels;

import entities.Team;
import main.Game;
import quests.QuestGiver;
import visuals.Frame;
import visuals.secondaryPanels.SecondaryPanel;

import javax.swing.*;
import java.awt.*;

public class ActiveQuestsPanel extends PrimaryPanel
{
    private Team team;

    private JButton[] quests;

    public ActiveQuestsPanel(Game game, Frame frame)
    {
        super(game, frame);
        this.team = game.getTeam();

        this.quests = new JButton[team.getActiveQuests().size()];
        for (int i = 0; i < team.getActiveQuests().size(); i++)
        {
            quests[i] = new JButton(team.getActiveQuests().get(i).getName());
            quests[i].setBounds(0, 20 + i*20, 250, 20);
            quests[i].setToolTipText(team.getActiveQuests().get(i).getDescription());
            quests[i].addActionListener(actionEvent ->
            {
                frame.requestFocus();
            });
            add(quests[i]);
        }
    }
}
