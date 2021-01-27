package controller;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoadSaveController extends Controller
{
    @FXML
    private VBox savesBox;

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
            button.setOnAction(event -> mainController.loadGame(fileEntry));
            savesBox.getChildren().add(button);
        }
    }

    @FXML
    public void handleBack()
    {
        mainController.openStartView();
    }
}
