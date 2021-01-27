package controller;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Singleton
public class MainController implements Initializable
{
    private Injector injector;

    private Stage stage;

    public MainController(Injector injector, Stage primaryStage)
    {
        this.injector = injector;
        this.stage = primaryStage;
    }

    public void initRootLayout() throws IOException
    {
        URL location = MainController.class.getResource("../view/StartView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        fxmlLoader.setControllerFactory(injector::getInstance);

        BorderPane rootLayout = fxmlLoader.load();

        ((Controller) fxmlLoader.getController()).setMainController(this);

        // add layout to a scene and show them all
        configureStage(stage, rootLayout);
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
            BorderPane root = fxmlLoader.load();
            configureStage(stage, root);
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
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
