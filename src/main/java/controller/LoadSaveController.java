package controller;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoadSaveController extends Controller
{
    @FXML
    private VBox savesBox;

    @FXML
    private VBox infoBox;

    @Inject
    public LoadSaveController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        File folder = new File("saveGames");
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles()))
        {
            Button button = new Button(fileEntry.getName());
            button.setOnAction(event -> showSaveInfo(fileEntry));
            savesBox.getChildren().add(button);
        }
    }

    private void showSaveInfo(File saveFile)
    {
        infoBox.getChildren().clear();
        infoBox.getChildren().add(new Label("Save name: " + saveFile.getName()));
        infoBox.getChildren().add(new Label("Last modified: " + new Date(saveFile.lastModified())));
        Button button = new Button("Load save");
        button.setOnAction(event -> mainController.loadGame(saveFile));
        infoBox.getChildren().add(button);
    }

    @FXML
    public void handleBack()
    {
        mainController.openStartView();
    }
}
