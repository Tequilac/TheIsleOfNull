package controller;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.classes.Class;
import model.game.Game;
import model.races.Race;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class MainController implements Initializable
{
    private final Injector injector;

    private final Stage stage;

    private final Game game;

    public MainController(Injector injector, Stage primaryStage)
    {
        this.injector = injector;
        this.stage = primaryStage;
        this.game = injector.getInstance(Game.class);
    }

    public void initRootLayout()
    {
        openStartView();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }

    public void changeStage(String name)
    {
        try
        {
            URL location = MainController.class.getResource("../view/" + name + ".fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            fxmlLoader.setControllerFactory(injector::getInstance);
            BorderPane root = fxmlLoader.load();
            ((Controller) fxmlLoader.getController()).setMainController(this);
            ((Controller) fxmlLoader.getController()).initView();
            configureStage(stage, root);
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

    public void openStartView()
    {
        changeStage("StartView");
    }

    public void openCreationView()
    {
        changeStage("CharacterCreationView");
    }

    public void configureStage(Stage primaryStage, BorderPane rootLayout)
    {
        rootLayout.setMinWidth(800);
        rootLayout.setMinHeight(600);
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("The Isle of Null");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
    }

    public void loadGame(File saveFile)
    {
        System.out.println(saveFile);
    }

    public List<Class> getClasses()
    {
        return game.getClasses();
    }

    public List<Race> getRaces()
    {
        return game.getRaces();
    }
}
