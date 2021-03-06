package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.entities.Character;
import model.entities.MagicCharacter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TeamController extends Controller
{
    @FXML
    private VBox charactersBox;

    private final List<VBox> characterBoxes = new ArrayList<>(4);

    private final List<Label> nameLabels = new ArrayList<>(4);

    private final List<Label> healthLabels = new ArrayList<>(4);

    private final List<Label> manaLabels = new ArrayList<>(4);

    private final List<Canvas> canvases = new ArrayList<>(4);

    @Inject
    public TeamController()
    {
    }

    @Override
    public void initView()
    {
        charactersBox.setStyle("-fx-background-color: #979797;");
        ArrayList<Character> teamMembers = mainController.getGame().getTeam().getTeamMembers();
        super.initView();

        for(int i = 0; i < 4; i++)
        {
            Character character = teamMembers.get(i);
            VBox child = new VBox();
            child.setMinHeight(250);

            Label name = new Label(character.getName());
            Label health = new Label(character.getHealth() + "/" + character.getMaxHealth());

            nameLabels.add(name);
            healthLabels.add(health);
            child.getChildren().add(name);
            child.getChildren().add(health);

            if(character instanceof MagicCharacter magicCharacter)
            {
                Label mana = new Label(magicCharacter.getMana() + "/" + magicCharacter.getMaxMana());
                manaLabels.add(mana);
                child.getChildren().add(mana);
            }

            Canvas canvas = new Canvas(300, 250);
            child.getChildren().add(canvas);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.fillRect(10, 10, 10, 10);

            characterBoxes.add(child);
            charactersBox.getChildren().add(child);
        }
    }
}
