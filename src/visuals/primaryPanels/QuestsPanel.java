package visuals.primaryPanels;

import main.Game;
import map.Chest;
import quests.Quest;
import quests.QuestGiver;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;

public class QuestsPanel extends PrimaryPanel
{
    private QuestGiver questGiver;

    private JButton[] quests;

    private JButton closeButton;

    public QuestsPanel(Game game, QuestGiver questGiver, Frame frame)
    {
        super(game, frame);

        setLayout(null);
        this.questGiver = questGiver;


        closeButton = new JButton(new ImageIcon("res/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            game.doAction();
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
        this.quests = new JButton[questGiver.getQuests().size()];
        for (int i = 0; i < questGiver.getQuests().size(); i++)
        {
            Quest quest = questGiver.getQuests().get(i);
            quests[i] = new JButton(quest.getName());
            quests[i].setBounds(0, 20 + i*20, 250, 20);
            quests[i].setToolTipText("<html>" + quest.toString() + "</html>");
            int finalI = i;
            if(!quest.isCompleted())
            {
                quests[i].addActionListener(actionEvent ->
                {
                    game.takeQuest(questGiver.getQuests().get(finalI));
                    frame.requestFocus();
                });
            }
            else
            {
                quests[i].setBackground(Color.GREEN);
                quests[i].addActionListener(actionEvent ->
                {
                    game.completeQuest(questGiver, finalI);
                    drawQuests();
                    frame.requestFocus();
                });
            }
            add(quests[i]);
        }
    }
}
