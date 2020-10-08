package visuals.primaryPanels;

import inhabitants.Trainer;
import main.Game;
import visuals.Frame;

import javax.swing.*;

public class TrainerPanel extends PrimaryPanel
{
    private Trainer trainer;

    private JLabel[] skills;

    private JButton closeButton;

    public TrainerPanel(Game game, Frame frame, Trainer trainer)
    {
        super(game, frame);

        this.trainer = trainer;

        closeButton = new JButton(new ImageIcon("res/graphics/exit.png"));
        closeButton.addActionListener(actionEvent ->
        {
            game.doAction();
            frame.updateButtons(true, false);
        });
        closeButton.setBounds(750, 10, 400, 40);
        add(closeButton);

        drawOfferedItem();
    }

    public void drawOfferedItem()
    {
        if(skills != null)
        {
            for (JLabel skill : skills)
            {
                if (skill != null)
                    remove(skill);
            }
        }
        this.skills = new JLabel[trainer.getTaughtSkills().size()];


        for (int i = 0; i < trainer.getTaughtSkills().size(); i++)
        {
            skills[i] = new JLabel(trainer.getTaughtSkills().get(i).getName());
            skills[i].setBounds(0, 20 + i*50, 40, 40);
            add(skills[i]);
        }
        updateUI();
        frame.requestFocus();
    }
}
