package visuals.primaryPanels;

import entities.Character;
import inhabitants.Trainer;
import main.Game;
import skills.Skill;
import skills.TaughtSkill;
import visuals.Frame;

import javax.swing.*;
import java.util.ArrayList;

public class TrainerPanel extends PrimaryPanel
{
    private Trainer trainer;

    private JLabel[] skills;

    private JButton[] learnButtons;

    private JButton closeButton;

    public TrainerPanel(Game game, Frame frame, Trainer trainer)
    {
        super(game, frame);

        setLayout(null);

        this.trainer = trainer;

        closeButton = new JButton(new ImageIcon("res/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            game.doAction();
            frame.updateButtons(true, false);
        });
        closeButton.setBounds(750, 10, 40, 40);
        add(closeButton);

        drawTaughtSkills(game.getCurrentCharacter());
    }

    public void drawTaughtSkills(Character character)
    {
        if(skills != null)
        {
            for (JLabel skill : skills)
            {
                if (skill != null)
                    remove(skill);
            }
            for(JButton button : learnButtons)
            {
                if (button != null)
                    remove(button);
            }
        }
        ArrayList<TaughtSkill> taughtSkills = trainer.getTaughtSkills();
        this.skills = new JLabel[taughtSkills.size()];
        this.learnButtons = new JButton[taughtSkills.size()];


        for (int i = 0; i < taughtSkills.size(); i++)
        {
            TaughtSkill taughtSkill = taughtSkills.get(i);
            skills[i] = new JLabel(taughtSkill.getName());
            skills[i].setBounds(0, i*50 + 10, 80, 40);
            add(skills[i]);

            learnButtons[i] = new JButton(new ImageIcon("res/graphics/upgrade_skill.png"));
            learnButtons[i].setBounds(100, i*50 + 10, 40, 40);
            boolean canBeTaught = false;
            for(Skill characterSkill : character.getSkills())
            {
                if(characterSkill.getName().equals(taughtSkill.getName()))
                    canBeTaught = taughtSkill.canBeTaught(characterSkill);
            }
            canBeTaught = canBeTaught || (taughtSkill.getLevelTaught() == 1 && game.getSkill(taughtSkill.getName()).getClasses().contains(character.getCharacterClass().getName()));
            learnButtons[i].setEnabled(canBeTaught);
            add(learnButtons[i]);
        }
        updateUI();
        frame.requestFocus();
    }
}
