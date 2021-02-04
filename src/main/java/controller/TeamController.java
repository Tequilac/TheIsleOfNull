package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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

    private final List<Label> nameLabels = new ArrayList<>(4);

    private final List<Label> healthLabels = new ArrayList<>(4);

    private final List<Label> manaLabels = new ArrayList<>(4);

    @Inject
    public TeamController()
    {
    }

    @Override
    public void initView()
    {
        ArrayList<Character> teamMembers = mainController.getGame().getTeam().getTeamMembers();
        super.initView();

        for(int i = 0; i < 4; i++)
        {
            Character character = teamMembers.get(i);
            VBox child = new VBox();

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
            characterBoxes.add(child);
            charactersBox.getChildren().add(child);
        }
    }
}
