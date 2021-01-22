package visuals.primaryPanels.teamPanels;

import entities.Character;
import game.Game;
import skills.Skill;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;
import java.util.ArrayList;

public class SkillsPanel extends PrimaryPanel
{
    private JLabel skillPoints;

    private JLabel[] skillLabels;

    private JButton[] increaseButtons;

    public SkillsPanel(Game game, Frame frame)
    {
        super(game, frame);

        displaySkills(game.getCurrentCharacter());
    }

    private void displaySkills(Character character)
    {
        if(skillPoints != null)
        {
            remove(skillPoints);
            for(JLabel skillLabel : skillLabels)
            {
                remove(skillLabel);
            }
            for(JButton button : increaseButtons)
            {
                remove(button);
            }
            repaint();
        }
        ArrayList<Skill> skills = character.getSkills();

        skillPoints = new JLabel("Skill points: " + character.getSkillPoints());
        skillPoints.setBounds(50, 10, 120, 20);
        add(skillPoints);

        skillLabels = new JLabel[3*skills.size()];
        increaseButtons = new JButton[skills.size()];
        for(int i = 0; i < skills.size(); i++)
        {
            Skill skill = skills.get(i);
            skillLabels[3*i] = new JLabel(skill.getName());
            skillLabels[3*i + 1] = new JLabel("Value: " + skill.getValue());
            skillLabels[3*i + 2] = new JLabel("Level: " + skill.getLevel());
            increaseButtons[i] = new JButton(new ImageIcon("src/main/resources/graphics/upgrade_skill.png"));

            skillLabels[3*i].setBounds(50, 30 + 90*i, 120, 30);
            skillLabels[3*i + 1].setBounds(50, 60 + 90*i, 120, 30);
            skillLabels[3*i + 2].setBounds(50, 90 + 90*i, 120, 30);
            increaseButtons[i].setBounds(200, 60 + 90*i, 40, 40);

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
