import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import controllers.Controller;
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

public class MainApp extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        Injector injector = Guice.createInjector(new GameModule());
        MainController mainController = injector.getInstance(MainController.class);

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
        mainController.configureStage(primaryStage, rootLayout);
        primaryStage.show();
    }
}
