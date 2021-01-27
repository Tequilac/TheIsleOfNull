package visuals;

import model.entities.MagicCharacter;
import model.entities.Team;
import model.game.Game;
import model.items.ItemParser;
import model.items.SpellBookItem;
import model.magic.SpellParser;
import visuals.primaryPanels.teamPanels.TeamPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class TeamInventoryPanel extends JPanel implements DragGestureListener
{
    private Game game;

    private TeamPanel teamPanel;

    private Team team;

    private JLabel[] items;

    public TeamInventoryPanel(Game game, TeamPanel teamPanel, Team team)
    {
        super();
        this.game = game;
        this.teamPanel = teamPanel;
        this.team = team;

        drawItems();
    }

    public void drawItems()
    {
        DragSource ds = new DragSource();

        if(this.items != null)
        {
            for(JLabel item : items)
            {
                remove(item);
            }
        }
        updateUI();

        this.items = new JLabel[team.getItemsInInventory().size()];
        for (int i = 0; i < team.getItemsInInventory().size(); i++)
        {
            items[i] = team.getItemsInInventory().get(i).getLabel();
            items[i].setBounds(0, 20 + i*40, 40, 40);
            items[i].setToolTipText("<html>" + team.getItemsInInventory().get(i).toString() + "</html>");

            if(team.getItemsInInventory().get(i) instanceof SpellBookItem)
            {
                int finalI = i;
                items[i].addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if(game.getCurrentCharacter() instanceof MagicCharacter)
                        {
                            try
                            {
                                ((MagicCharacter) game.getCurrentCharacter()).learnSpell(SpellParser.parseSpell(((SpellBookItem)team.getItemsInInventory().get(finalI)).getSpellName()));
                                team.removeItemFromInventory(team.getItemsInInventory().get(finalI));
                                drawItems();
                                teamPanel.updateSpellsPanel();
                            }
                            catch(IOException ioException)
                            {
                                ioException.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseExited(MouseEvent e)
                    {

                    }
                });
            }
            else
            {
                ds.createDefaultDragGestureRecognizer(items[i], DnDConstants.ACTION_COPY, this);
            }

            add(items[i]);
        }
    }

    public Team getTeam()
    {
        return team;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event)
    {
        var cursor = Cursor.getDefaultCursor();
        JLabel label = (JLabel) event.getComponent();
        String text = label.getToolTipText();
        text = text.substring(6, text.indexOf("<br>"));

        if(event.getDragAction() == DnDConstants.ACTION_COPY)
        {
            cursor = DragSource.DefaultCopyDrop;
        }

        try
        {
            event.startDrag(cursor, new TransferableItem(ItemParser.parseItem(text)));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}