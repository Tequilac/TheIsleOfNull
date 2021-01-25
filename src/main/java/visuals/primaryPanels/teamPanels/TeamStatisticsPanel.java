package visuals.primaryPanels.teamPanels;

import model.entities.Character;
import model.game.Game;
import visuals.Frame;
import visuals.primaryPanels.PrimaryPanel;

import javax.swing.*;

public class TeamStatisticsPanel extends PrimaryPanel
{
    public TeamStatisticsPanel(Game game, Frame frame)
    {
        super(game, frame);

        drawCharacter(game.getCurrentCharacter());
    }

    private void drawCharacter(Character character)
    {
        JLabel name = new JLabel(character.getName());
        JLabel characterClass = new JLabel("Class: " + character.getCharacterClass().getName());
        JLabel race = new JLabel("Race: " + character.getRace().getName());
        JLabel level = new JLabel("Level: " + character.getLevel());
        JLabel experience = new JLabel("Experience: " + character.getExperience());
        JLabel magicUsage = new JLabel("Can" + ((character.getCharacterClass().usesMagic())? "" : "'t") + " use model.magic");
        JLabel teamGold = new JLabel("Team gold: " + game.getTeam().getGold());
        name.setBounds(50, 50, 120, 30);
        add(name);
        characterClass.setBounds(50, 80, 120, 30);
        add(characterClass);
        race.setBounds(50, 110, 120, 30);
        add(race);
        level.setBounds(50, 140, 120, 30);
        add(level);
        experience.setBounds(50, 170, 120, 30);
        add(experience);
        magicUsage.setBounds(50, 410, 120, 30);
        add(magicUsage);
        teamGold.setBounds(50, 440, 120, 30);
        add(teamGold);
        drawAttributes(character);
    }

    private void drawAttributes(Character character)
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
    }
}
