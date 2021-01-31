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

        saveNameField.textProperty().addListener(((value, oldValue, newValue) -> updateSubmitButton()));

        for(int i = 0; i < 4; i++)
        {
            TextField name = new TextField("Choose name");
            ComboBox<Class> characterClass = new ComboBox<>(FXCollections.observableList(mainController.getGame().getClasses()));
            ComboBox<Race> characterRace = new ComboBox<>(FXCollections.observableList(mainController.getGame().getRaces()));

            namesFields.add(name);
            classesBoxes.add(characterClass);
            racesBoxes.add(characterRace);
            VBox characterBox = new VBox(name, characterClass, characterRace);
            charactersBox.getChildren().add(characterBox);
        }

        classesBoxes.forEach(box -> box.getSelectionModel().selectedItemProperty().addListener(
                ((value, oldValue, newValue) -> updateSubmitButton())));
        racesBoxes.forEach(box -> box.getSelectionModel().selectedItemProperty().addListener(
                ((value, oldValue, newValue) -> updateSubmitButton())));
    }

    private void updateSubmitButton()
    {
        submitButton.setDisable(mainController.getSaveSystem().saveExists(saveNameField.getText()) || !boxesFilled());
    }

    private boolean boxesFilled()
    {
        return classesBoxes.stream().allMatch(box -> box.getSelectionModel().getSelectedItem() != null)
                && racesBoxes.stream().allMatch(box -> box.getSelectionModel().getSelectedItem() != null);
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

        mainController.getGame().createTeam(names, classes, races);
        mainController.openGameView();
    }
}
