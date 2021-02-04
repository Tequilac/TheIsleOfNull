package controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameController extends Controller
{
    private final OptionsController optionsController;

    private final  MainPaneController mainPaneController;

    private final TeamController teamController;

    @Inject
    public GameController(OptionsController optionsController, MainPaneController mainPaneController, TeamController teamController)
    {
        this.optionsController = optionsController;
        this.mainPaneController = mainPaneController;
        this.teamController = teamController;
        childrenControllers.add(optionsController);
        childrenControllers.add(mainPaneController);
        childrenControllers.add(teamController);
    }
}
