package controller;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.game.Game;
import model.game.MapSystem;
import model.game.SaveSystem;

import java.io.File;
import java.net.URL;

@Singleton
public class MainController
{
    private final Injector injector;

    private final Stage stage;

    private final Game game;

    private final MapSystem mapSystem;

    private final SaveSystem saveSystem;

    private Controller currentController;

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
        changeStage("initialViews/LoadSaveView");
    }

    public void openStartView()
    {
        changeStage("initialViews/StartView");
    }

    public void openCreationView()
    {
        changeStage("initialViews/CharacterCreationView");
    }

    public void openGameView()
    {
        currentController = injector.getInstance(GameController.class);
        changeStage("GameView");
        stage.getScene().setOnKeyPressed(event -> ((GameController) currentController).handleKey(event.getCode()));
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
}
