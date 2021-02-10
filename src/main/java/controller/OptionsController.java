package controller;

import com.google.inject.Singleton;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

@Singleton
public class OptionsController extends Controller
{
    @FXML
    private VBox optionsBox;

    @Override
    public void initView()
    {
        super.initView();

        optionsBox.setStyle("-fx-background-color: #979797;");
    }
}
