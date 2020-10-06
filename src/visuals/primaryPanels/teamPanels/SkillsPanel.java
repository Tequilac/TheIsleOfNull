package visuals.primaryPanels.teamPanels;

import entities.Character;
import main.Game;
import skills.Skill;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SkillsPanel extends PrimaryPanel
{

    public SkillsPanel(Game game, Frame frame)
    {
        super(game, frame);
        setLayout(null);
        displaySkills(game.getCurrentCharacter());
    }

    public void displaySkills(Character character)
    {
        ArrayList<Skill> skills = character.getSkills();
        JLabel[] skillLabels = new JLabel[3*skills.size()];
        JButton[] increaseButtons = new JButton[skills.size()];
        for(int i = 0; i < skills.size(); i++)
        {
            Skill skill = skills.get(i);
            skillLabels[3*i] = new JLabel(skill.getName());
            skillLabels[3*i + 1] = new JLabel("Value: " + skill.getValue());
            skillLabels[3*i + 2] = new JLabel("Level: " + skill.getLevel());
            increaseButtons[i] = new JButton("Increase");

            skillLabels[3*i].setBounds(50, 30 + 90*i, 120, 30);
            skillLabels[3*i + 1].setBounds(50, 60 + 90*i, 120, 30);
            skillLabels[3*i + 2].setBounds(50, 90 + 90*i, 120, 30);
            increaseButtons[i].setBounds(200, 60 + 90*i, 30, 30);

            if(character.getSkillPoints() > skill.getValue())
            {
                increaseButtons[i].addActionListener(e ->
                {
                    skill.increaseValue();
                    character.decreaseSkillPoints(skill.getValue());
                    displaySkills(character);
                });
            }
            else
            {
                increaseButtons[i].setEnabled(false);
            }
            add(skillLabels[3*i]);
            add(skillLabels[3*i + 1]);
            add(skillLabels[3*i + 2]);
            add(increaseButtons[i]);
        }
    }
}
