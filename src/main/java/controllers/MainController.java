package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.game.Game;

import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class MainController implements Initializable
{
    @Inject
    public MainController()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public void changeStage(String name)
    {
        try
        {
            URL location = getClass().getResource("/view/" + name + ".fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //main.stg.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void openLoadView()
    {
        changeStage("LoadSaveView");
    }

    public void configureStage(Stage primaryStage, BorderPane rootLayout)
    {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Isle of Null");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }
}
