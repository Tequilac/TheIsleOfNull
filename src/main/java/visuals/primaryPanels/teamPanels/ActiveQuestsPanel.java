package visuals.primaryPanels.teamPanels;

import entities.Team;
import game.Game;
import quests.Quest;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.awt.*;

public class ActiveQuestsPanel extends PrimaryPanel
{
    public ActiveQuestsPanel(Game game, Frame frame)
    {
        super(game, frame);

        drawQuests();
    }

    private void drawQuests()
    {
        Team team = game.getTeam();
        JButton[] quests = new JButton[team.getActiveQuests().size()];
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
