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
import model.game.MapSystem;
import model.game.SaveSystem;
import model.races.Race;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class MainController
{
    private final Injector injector;

    private final Stage stage;

    private final Game game;

    private final MapSystem mapSystem;

    private final SaveSystem saveSystem;

    public MainController(Injector injector, Stage primaryStage)
    {
        this.injector = injector;
        this.stage = primaryStage;
        this.game = injector.getInstance(Game.class);
        this.mapSystem = game.getMapSystem();
        this.saveSystem = game.getSaveSystem();
    }

    public void initRootLayout()
    {
        openStartView();
        stage.show();
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

    public Game getGame()
    {
        return game;
    }

    public MapSystem getMapSystem()
    {
        return mapSystem;
    }

    public SaveSystem getSaveSystem()
    {
        return saveSystem;
    }

    public void createTeam(List<String> names, List<Class> classes, List<Race> races)
    {
        game.createTeam(names, classes, races);
    }
}
