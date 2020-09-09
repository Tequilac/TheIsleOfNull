package visuals.secondaryPanels;

import entities.Group;
import main.Game;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OptionsPanel extends SecondaryPanel
{
    private JLabel[] enemiesInfo;

    private JButton actionButton;

    private JButton hitButton;

    private JButton inventoryButton;

    private JButton optionsButton;

    private JButton exitButton;

    public OptionsPanel(Game game, Frame frame)
    {
        super(game, frame);

        enemiesInfo = new JLabel[10];
        for (int i = 0; i < 10; i++)
        {
            enemiesInfo[i] = new JLabel();
            enemiesInfo[i].setBounds(5, 200 + i*20, 100, 20);
            add(enemiesInfo[i]);
        }

        actionButton = new JButton(new ImageIcon("res/graphics/action.png"));
        actionButton.addActionListener(actionEvent ->
        {
            try
            {
                game.doAction();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            frame.requestFocus();
        });
        actionButton.setBounds(5, 5, 40, 40);
        add(actionButton);


        hitButton = new JButton(new ImageIcon("res/graphics/hit.png"));
        hitButton.addActionListener(actionEvent ->
        {
            game.teamHit();
            setEnemiesName();
            frame.getMainPanel().repaint();
            frame.requestFocus();
        });
        hitButton.setBounds(50, 5, 40, 40);
        add(hitButton);


        inventoryButton = new JButton(new ImageIcon("res/graphics/backpack.png"));
        inventoryButton.addActionListener(actionEvent ->
        {
            frame.toggleTeamView();
            frame.requestFocus();
        });
        inventoryButton.setBounds(95, 5, 40, 40);
        add(inventoryButton);


        optionsButton = new JButton(new ImageIcon("res/graphics/options.png"));
        optionsButton.addActionListener(actionEvent ->
        {
        });
        optionsButton.setBounds(140, 5, 40, 40);
        add(optionsButton);

        exitButton = new JButton(new ImageIcon("res/graphics/exit.png"));
        exitButton.addActionListener(actionEvent ->
        {
            try
            {
                game.saveGame();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            frame.exitGame();
        });
        exitButton.setBounds(185, 5, 40, 40);
        add(exitButton);
    }

    public void setEnemiesName()
    {
        Group enemies = game.getChosenEnemies();
        for (int i = 1; i < 10; i++)
        {
            enemiesInfo[i].setText("");
        }
        if(enemies != null && enemies.getEntities() != null && !enemies.getEntities().isEmpty())
        {
            enemiesInfo[0].setText("Enemies in sight:");
            enemiesInfo[1].setText(enemies.getEntities().size() + " of " + enemies.getEntities().get(0).getName());
            for (int i = 2; i < enemies.getEntities().size()+2; i++)
            {
                enemiesInfo[i].setText(enemies.getEntities().get(0).getName() + " health: " + enemies.getEntities().get(i-2).getHealth());
            }
        }
    }
}
