package controller;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.classes.Class;
import model.races.Race;

import java.net.URL;
import java.util.ResourceBundle;

public class CharacterCreationController extends Controller
{
    @FXML
    private HBox charactersBox;

    @Inject
    public CharacterCreationController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    @Override
    public void initView()
    {
        super.initView();

        for(int i = 0; i < 4; i++)
        {
            javafx.scene.control.TextField name = new TextField("Choose name");
            ComboBox<Class> characterClass = new ComboBox<>(FXCollections.observableList(mainController.getClasses()));
            ComboBox<Race> characterRace = new ComboBox<>(FXCollections.observableList(mainController.getRaces()));

            VBox characterBox = new VBox(name, characterClass, characterRace);
            charactersBox.getChildren().add(characterBox);
        }
    }

    public void handleBack()
    {
        mainController.openStartView();
    }
}
