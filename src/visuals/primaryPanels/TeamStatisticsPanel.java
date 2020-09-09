package visuals.primaryPanels;

import entities.Character;
import entities.MagicCharacter;
import main.Game;
import visuals.Frame;

import javax.swing.*;

public class TeamStatisticsPanel extends PrimaryPanel
{
    private Character currentCharacter;

    public TeamStatisticsPanel(Game game, Frame frame)
    {
        super(game, frame);

        drawCharacter(game.getCurrentCharacter());
    }

    public void drawCharacter(Character character)
    {
        JLabel name = new JLabel(character.getName());
        JLabel characterClass = new JLabel(character.getCharacterClass().getName());
        JLabel race = new JLabel(character.getRace().getName());
        JLabel magicUsage = new JLabel("Can" + ((character instanceof MagicCharacter)? "" : "'t") + " use magic");
        name.setBounds(50, 50, 120, 30);
        add(name);
        characterClass.setBounds(50, 100, 120, 30);
        add(characterClass);
        race.setBounds(50, 150, 120, 30);
        add(race);
        drawAttributes(character, magicUsage);
    }

    public void drawAttributes(Character character, JLabel magicUsage)
    {

        JLabel[] attributes = new JLabel[7];
        for (int j = 0; j < 7; j++)
        {
            attributes[j] = new JLabel();
            attributes[j].setBounds(50,j*30 + 200, 120,30);
            add(attributes[j]);
        }
        attributes[0].setText("Might: " + character.getMight());
        attributes[1].setText("Intellect: " + character.getIntellect());
        attributes[2].setText("Personality: " + character.getPersonality());
        attributes[3].setText("Endurance: " + character.getEndurance());
        attributes[4].setText("Accuracy: " + character.getAccuracy());
        attributes[5].setText("Speed: " + character.getSpeed());
        attributes[6].setText("Luck: " + character.getLuck());

        magicUsage = new JLabel();
        magicUsage.setBounds(50, 410, 120, 30);
        add(magicUsage);
    }
}
