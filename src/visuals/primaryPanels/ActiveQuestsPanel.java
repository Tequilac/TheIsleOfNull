package visuals.primaryPanels;

import entities.Team;
import main.Game;
import quests.Quest;
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
            Quest quest = team.getActiveQuests().get(i);
            quests[i] = new JButton(quest.getName());
            quests[i].setBounds(0, 20 + i*20, 250, 20);
            quests[i].setToolTipText("<html>" + quest.toString() + "</html>");
            if(quest.isCompleted())
                quests[i].setBackground(Color.GREEN);
            quests[i].addActionListener(actionEvent ->
                    frame.requestFocus());
            add(quests[i]);
        }
    }
}
