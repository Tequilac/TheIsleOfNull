package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXML;

@Singleton
public class StartViewController extends Controller
{
    @Inject
    public StartViewController()
    {
    }

    @FXML
    public void handleNewGame()
    {
        mainController.openCreationView();
    }

    @FXML
    public void handleLoadGame()
    {
        mainController.openLoadView();
    }
}
