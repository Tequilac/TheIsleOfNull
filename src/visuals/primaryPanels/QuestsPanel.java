package visuals.primaryPanels;

import main.Game;
import map.Chest;
import quests.QuestGiver;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class QuestsPanel extends PrimaryPanel
{
    private QuestGiver questGiver;

    private JButton[] quests;

    public QuestsPanel(Game game, QuestGiver questGiver, Frame frame)
    {
        super(game, frame);

        setLayout(null);
        this.questGiver = questGiver;

        this.quests = new JButton[questGiver.getQuests().size()];
        for (int i = 0; i < questGiver.getQuests().size(); i++)
        {
            quests[i] = new JButton(questGiver.getQuests().get(i).getName());
            quests[i].setBounds(0, 20 + i*20, 250, 20);
            quests[i].setToolTipText(questGiver.getQuests().get(i).getDescription());
            int finalI = i;
            quests[i].addActionListener(actionEvent ->
            {
                frame.getGame().takeQuest(questGiver.getQuests().get(finalI));
                frame.requestFocus();
            });
            add(quests[i]);
        }
    }
}
