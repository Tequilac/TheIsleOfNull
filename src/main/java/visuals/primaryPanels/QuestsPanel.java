package visuals.primaryPanels;

import model.game.Game;
import model.quests.Quest;
import model.quests.QuestGiver;
import model.quests.QuestStatus;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuestsPanel extends PrimaryPanel
{
    private QuestGiver questGiver;

    private JButton[] quests;

    private JButton closeButton;

    public QuestsPanel(Game game, QuestGiver questGiver, Frame frame)
    {
        super(game, frame);

        this.questGiver = questGiver;


        closeButton = new JButton(new ImageIcon("src/main/resources/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            //game.doAction();
            frame.updateButtons(true, false);
        });
        closeButton.setBounds(750, 10, 40, 40);
        add(closeButton);

        drawQuests();
    }

    public void drawQuests()
    {
        if(quests != null)
        {
            for(JButton quest : quests)
            {
                remove(quest);
            }
            repaint();
        }
        ArrayList<Quest> availableQuests = questGiver.getQuests();
        availableQuests.removeIf(quest -> quest.getQuestStatus().equals(QuestStatus.Completed));
        this.quests = new JButton[availableQuests.size()];
        for (int i = 0; i < availableQuests.size(); i++)
        {
            Quest quest = availableQuests.get(i);
            quests[i] = new JButton(quest.getName());
            quests[i].setBounds(0, 20 + i*20, 250, 20);
            quests[i].setToolTipText("<html>" + quest.toString() + "</html>");
            int finalI = i;
            if(quest.isCompleted())
            {
                quests[i].setBackground(Color.GREEN);
                quests[i].addActionListener(actionEvent ->
                {
                    game.completeQuest(questGiver, finalI);
                    drawQuests();
                    frame.requestFocus();
                });
            }
            else if(quest.getQuestStatus().equals(QuestStatus.Available))
            {
                quests[i].addActionListener(actionEvent ->
                {
                    game.takeQuest(questGiver.getQuests().get(finalI));
                    drawQuests();
                    frame.requestFocus();
                });
            }
            else
            {
                quests[i].setBackground(Color.BLUE);
                quests[i].setEnabled(false);
            }
            add(quests[i]);
        }
    }
}
