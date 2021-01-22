package visuals.primaryPanels;

import entities.Race;
import game.Game;
import skills.Skill;
import visuals.Frame;
import entities.Character;
import entities.Class;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterCreationPanel extends PrimaryPanel implements ActionListener
{
    private JTextField[] names;

    private JTextField saveName;

    public CharacterCreationPanel(Game game, Frame frame)
    {
        super(game, frame);
        setFocusable(true);
        drawCharacters(game);
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(1200, 700, 100, 20);
        submitButton.addActionListener(this);
        add(submitButton);
        JLabel saveLabel = new JLabel("Save game name:");
        saveLabel.setBounds(1000, 600, 150, 20);
        add(saveLabel);
        saveName = new JTextField("save");
        saveName.setBounds(1000, 650, 150, 50);
        add(saveName);
    }

    public void drawCharacters(Game game)
    {
        names = new JTextField[4];

        String[] classes = new String[game.getClasses().size()];
        Object[] classesArray = game.getClasses().values().toArray();
        for (int i = 0; i < game.getClasses().size(); i++)
        {
            classes[i] = ((Class)(classesArray[i])).getName();
        }
        JComboBox[] classChoosers = new JComboBox[4];

        String[] races = new String[game.getRaces().size()];
        Object[] racesArray = game.getRaces().values().toArray();
        for (int i = 0; i < game.getRaces().size(); i++)
        {
            races[i] = ((Race)(racesArray[i])).getName();
        }
        JComboBox[] raceChoosers = new JComboBox[4];

        JLabel[] attributes = new JLabel[28];
        JLabel[] magicUsage = new JLabel[4];
        JLabel[] skills = new JLabel[12];
        for (int i = 0; i < game.getCharacters().size(); i++)
        {
            names[i] = new JTextField(game.getCharacters().get(i).getName());
            names[i].setBounds(i*400 + 50,50, 120,30);
            add(names[i]);
            classChoosers[i] = new JComboBox(classes);
            int finalI = i;
            classChoosers[i].addActionListener(actionEvent ->
            {
                game.getCharacters().get(finalI).setClass(game.getClasses().get(classes[classChoosers[finalI].getSelectedIndex()]));
                drawAttributes(game, attributes, magicUsage, skills, finalI);
            });
            classChoosers[i].setBounds(i*400 + 50,100, 120,30);
            add(classChoosers[i]);
            raceChoosers[i] = new JComboBox(races);
            int finalI1 = i;
            raceChoosers[i].addActionListener(actionEvent ->
            {
                game.getCharacters().get(finalI1).setRace(game.getRaces().get(races[raceChoosers[finalI1].getSelectedIndex()]));
                drawAttributes(game, attributes, magicUsage, skills, finalI1);
            });
            raceChoosers[i].setBounds(i*400 + 50,150, 120,30);
            add(raceChoosers[i]);
            drawAttributes(game, attributes, magicUsage, skills, i);
        }
    }

    public void drawAttributes(Game game, JLabel[] attributes, JLabel[] magicUsage, JLabel[] skills, int i)
    {
        Character character = game.getCharacters().get(i);
        if(attributes[i*7] == null)
        {
            for (int j = 0; j < 7; j++)
            {
                attributes[i*7 + j] = new JLabel();
                attributes[i*7 + j].setBounds(i*400 + 50,j*30 + 200, 120,30);
                add(attributes[i*7 + j]);
            }
        }
        attributes[i*7].setText("Might: " + character.getMight());
        attributes[i*7+1].setText("Intellect: " + character.getIntellect());
        attributes[i*7+2].setText("Personality: " + character.getPersonality());
        attributes[i*7+3].setText("Endurance: " + character.getEndurance());
        attributes[i*7+4].setText("Accuracy: " + character.getAccuracy());
        attributes[i*7+5].setText("Speed: " + character.getSpeed());
        attributes[i*7+6].setText("Luck: " + character.getLuck());

        if(magicUsage[i] == null)
        {
            magicUsage[i] = new JLabel();
            magicUsage[i].setBounds(i*400 + 50,410, 120,30);
            add(magicUsage[i]);
        }
        magicUsage[i].setText("Can" + (character.getCharacterClass().usesMagic()? "" : "'t") + " use magic");

        if(skills[i*3] == null)
        {
            for(int j = 0; j < 3; j++)
            {
                skills[i*3 + j] = new JLabel();
                skills[i*3 + j].setBounds(i*400 + 50, j*30 + 440, 120, 30);
                add(skills[i*3 + j]);
            }
        }
        ArrayList<Skill> characterSkills = character.getSkills();
        for(int j = 0; j < 3; j++)
        {
            skills[i*3 + j].setText(characterSkills.get(j).getName());
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        String[] nameTexts = new String[4];
        for (int i = 0; i < 4; i++)
        {
            nameTexts[i] = names[i].getText();
            game.getCharacters().get(i).setName(nameTexts[i]);
        }
        game.setCurrentSave(new File("saveGames/" + saveName.getText() + ".txt"));
        game.createTeam();
        try
        {
            game.saveGame();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        frame.openMapPanel();
    }
}
