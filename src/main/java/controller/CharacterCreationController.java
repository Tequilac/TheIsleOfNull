package controller;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.classes.Class;
import model.races.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterCreationController extends Controller
{
    @FXML
    private HBox charactersBox;

    @FXML
    private TextField saveNameField;

    @FXML
    private Button submitButton;

    private final List<TextField> namesFields = new ArrayList<>(4);

    private final List<ComboBox<Class>> classesBoxes = new ArrayList<>(4);

    private final List<ComboBox<Race>> racesBoxes = new ArrayList<>(4);

    @Inject
    public CharacterCreationController()
    {
    }

    @Override
    public void initView()
    {
        super.initView();

        submitButton.setDisable(true);

        saveNameField.textProperty().addListener(((value, oldValue, newValue) -> updateSubmitButton(newValue)));

        for(int i = 0; i < 4; i++)
        {
            TextField name = new TextField("Choose name");
            ComboBox<Class> characterClass = new ComboBox<>(FXCollections.observableList(mainController.getClasses()));
            ComboBox<Race> characterRace = new ComboBox<>(FXCollections.observableList(mainController.getRaces()));

            namesFields.add(name);
            classesBoxes.add(characterClass);
            racesBoxes.add(characterRace);
            VBox characterBox = new VBox(name, characterClass, characterRace);
            charactersBox.getChildren().add(characterBox);
        }

    }

    private void updateSubmitButton(String newValue)
    {
        submitButton.setDisable(mainController.saveExists(newValue));
    }

    @FXML
    public void handleBack()
    {
        mainController.openStartView();
    }

    @FXML
    public void handleSubmit()
    {
        List<String> names = namesFields.stream()
                .map(TextInputControl::getText)
                .collect(Collectors.toList());

        List<Class> classes = classesBoxes.stream()
                .map(box -> box.getSelectionModel().getSelectedItem())
                .collect(Collectors.toList());

        List<Race> races = racesBoxes.stream()
                .map(box -> box.getSelectionModel().getSelectedItem())
                .collect(Collectors.toList());

        mainController.createTeam(names, classes, races);
    }
}
