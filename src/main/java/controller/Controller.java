package controller;

import java.util.ArrayList;
import java.util.List;

public abstract class Controller
{
    protected MainController mainController;

    protected List<Controller> childrenControllers = new ArrayList<>();

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
        for(Controller child : childrenControllers)
        {
            child.setMainController(mainController);
        }
    }

    public void initView()
    {
        for(Controller child : childrenControllers)
        {
            child.initView();
        }
    }
}
