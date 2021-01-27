import com.google.inject.Guice;
import com.google.inject.Injector;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import guice.GameModule;

import java.io.IOException;

public class MainApp extends Application
{

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Injector injector = Guice.createInjector(new GameModule());
        MainController mainController = new MainController(injector, primaryStage);
        mainController.initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
