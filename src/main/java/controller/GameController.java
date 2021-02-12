package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.input.KeyCode;
import model.map.MoveDirection;

import static model.map.MoveDirection.*;

@Singleton
public class GameController extends Controller
{
    @Inject
    public GameController(OptionsController optionsController, MainPaneController mainPaneController, TeamController teamController)
    {
        childrenControllers.add(optionsController);
        childrenControllers.add(mainPaneController);
        childrenControllers.add(teamController);
    }

    public void handleKey(KeyCode code)
    {
        MoveDirection direction = null;
        switch(code)
        {
            case W -> direction = FRONT;
            case S -> direction = BACK;
            case A -> direction = LEFT;
            case D -> direction = RIGHT;
            case Q -> direction = TURN_LEFT;
            case E -> direction = TURN_RIGHT;
        }
        if(direction != null)
        {
            System.out.println(direction);
        }
    }
}
