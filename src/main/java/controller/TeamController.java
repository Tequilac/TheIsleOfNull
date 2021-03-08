package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.entities.Character;
import model.entities.MagicCharacter;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TeamController extends Controller
{
    @FXML
    private VBox charactersBox;

    private final List<VBox> characterBoxes = new ArrayList<>(4);

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
            child.setAlignment(Pos.CENTER);
            child.setMinHeight(250);

            Canvas canvas = new Canvas(300, 250);
            child.getChildren().add(canvas);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            gc.fillText(character.getName(), 10, 10);

            gc.setStroke(Color.GREEN);
            gc.setFill(Color.GREEN);
            gc.strokeRect(10, 40, 280, 20);
            gc.fillRect(10, 40, 280*character.getHealth()/(float)character.getMaxHealth(), 20);
            gc.setFill(Color.BLACK);
            gc.fillText(character.getHealth() + "/" + character.getMaxHealth(), 140, 55);

            if(character instanceof MagicCharacter magicCharacter)
            {
                gc.setStroke(Color.BLUE);
                gc.setFill(Color.BLUE);
                gc.strokeRect(10, 80, 280, 20);
                gc.fillRect(10, 80, 280*magicCharacter.getMana()/(float)magicCharacter.getMaxMana(), 20);
                gc.setFill(Color.BLACK);
                gc.fillText(magicCharacter.getMana() + "/" + magicCharacter.getMaxMana(), 140, 95);
            }

            characterBoxes.add(child);
            canvases.add(canvas);
            charactersBox.getChildren().add(child);
        }
    }
}
