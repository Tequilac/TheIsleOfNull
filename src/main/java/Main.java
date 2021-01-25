import com.google.inject.Guice;
import com.google.inject.Injector;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.game.Game;
import guice.GameModule;
import visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        Injector injector = Guice.createInjector(new GameModule());
        Game game = injector.getInstance(Game.class);

        var fxmlLoader = new FXMLLoader(getClass().getResource("view/StartView.fxml"));

        fxmlLoader.setControllerFactory(injector::getInstance);

        BorderPane rootLayout = null;
        try
        {
            rootLayout = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        // add layout to a scene and show them all
        configureStage(primaryStage, rootLayout);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane rootLayout)
    {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Isle of Null");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }
}
