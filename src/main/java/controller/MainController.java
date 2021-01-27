package controller;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class MainController implements Initializable
{
    private final Injector injector;

    private final Stage stage;

    public MainController(Injector injector, Stage primaryStage)
    {
        this.injector = injector;
        this.stage = primaryStage;
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
}
