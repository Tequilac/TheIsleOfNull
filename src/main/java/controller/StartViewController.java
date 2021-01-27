package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class StartViewController extends Controller
{
    @Inject
    public StartViewController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    @FXML
    public void handleNewGame()
    {
        System.out.println("New Game");
    }

    @FXML
    public void handleLoadGame()
    {
        mainController.openLoadView();
    }
}
