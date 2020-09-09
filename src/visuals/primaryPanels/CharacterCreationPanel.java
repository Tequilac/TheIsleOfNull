package visuals.primaryPanels;

import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CharacterCreationPanel extends PrimaryPanel implements ActionListener
{
    private JTextField[] names;

    private JTextField saveName;

    public CharacterCreationPanel(Game game, Frame frame)
    {
        super(game, frame);
        setFocusable(true);
        setLayout(null);
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
        for (int i = 0; i < game.getClasses().size(); i++)
        {
            classes[i] = game.getClasses().get(i).getName();
        }
        JComboBox[] classChoosers = new JComboBox[4];
        String races[] = new String[game.getRaces().size()];
        for (int i = 0; i < game.getRaces().size(); i++)
        {
            races[i] = game.getRaces().get(i).getName();
        }
        JComboBox[] raceChoosers = new JComboBox[4];
        JLabel[] attributes = new JLabel[28];
        JLabel[] magicUsage = new JLabel[4];
        for (int i = 0; i < game.getCharacters().size(); i++)
        {
            names[i] = new JTextField(game.getCharacters().get(i).getName());
            names[i].setBounds(i*400 + 50,50, 120,30);
            add(names[i]);
            classChoosers[i] = new JComboBox(classes);
            int finalI = i;
            classChoosers[i].addActionListener(actionEvent ->
            {
                game.getCharacters().get(finalI).setClass(game.getClasses().get((classChoosers[finalI].getSelectedIndex())));
                drawAttributes(game, attributes, magicUsage, finalI);
            });
            classChoosers[i].setBounds(i*400 + 50,100, 120,30);
            add(classChoosers[i]);
            raceChoosers[i] = new JComboBox(races);
            int finalI1 = i;
            raceChoosers[i].addActionListener(actionEvent ->
            {
                game.getCharacters().get(finalI1).setRace(game.getRaces().get(raceChoosers[finalI1].getSelectedIndex()));
                drawAttributes(game, attributes, magicUsage, finalI1);
            });
            raceChoosers[i].setBounds(i*400 + 50,150, 120,30);
            add(raceChoosers[i]);
            drawAttributes(game, attributes, magicUsage, i);
        }
    }

    public void drawAttributes(Game game, JLabel[] attributes, JLabel[] magicUsage, int i)
    {
        if(attributes[i*7] == null)
        {
            for (int j = 0; j < 7; j++)
            {
                attributes[i*7 + j] = new JLabel();
                attributes[i*7 + j].setBounds(i*400 + 50,j*30 + 200, 120,30);
                add(attributes[i*7 + j]);
            }
        }
        attributes[i*7].setText("Might: " + game.getCharacters().get(i).getMight());
        attributes[i*7+1].setText("Intellect: " + game.getCharacters().get(i).getIntellect());
        attributes[i*7+2].setText("Personality: " + game.getCharacters().get(i).getPersonality());
        attributes[i*7+3].setText("Endurance: " + game.getCharacters().get(i).getEndurance());
        attributes[i*7+4].setText("Accuracy: " + game.getCharacters().get(i).getAccuracy());
        attributes[i*7+5].setText("Speed: " + game.getCharacters().get(i).getSpeed());
        attributes[i*7+6].setText("Luck: " + game.getCharacters().get(i).getLuck());

        if(magicUsage[i] == null)
        {
            magicUsage[i] = new JLabel();
            magicUsage[i].setBounds(i*400 + 50,410, 120,30);
            add(magicUsage[i]);
        }
        magicUsage[i].setText("Can" + (game.getCharacters().get(i).getCharacterClass().usesMagic()? "" : "'t") + " use magic");
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
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        frame.openMapPanel();
    }
}
