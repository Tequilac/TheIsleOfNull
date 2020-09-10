package visuals.primaryPanels;

import entities.Character;
import main.Game;
import skills.Skill;
import visuals.Frame;

import javax.swing.*;
import java.util.ArrayList;

public class SkillsPanel extends PrimaryPanel
{

    public SkillsPanel(Game game, Frame frame)
    {
        super(game, frame);
        displaySkills(game.getCurrentCharacter());
    }

    public void displaySkills(Character character)
    {
        ArrayList<Skill> skills = character.getSkills();
        JLabel[] skillLabels = new JLabel[3*skills.size()];
        for(int i = 0; i < skills.size(); i++)
        {
            Skill skill = skills.get(i);
            skillLabels[3*i] = new JLabel(skill.getName());
            skillLabels[3*i + 1] = new JLabel("Value: " + skill.getValue());
            skillLabels[3*i + 2] = new JLabel("Level: " + skill.getLevel());

            skillLabels[3*i].setBounds(50, 30 + 30*i, 120, 30);
            skillLabels[3*i + 1].setBounds(50, 60 + 30*i, 120, 30);
            skillLabels[3*i + 2].setBounds(50, 90 + 30*i, 120, 30);
        }
    }
}
