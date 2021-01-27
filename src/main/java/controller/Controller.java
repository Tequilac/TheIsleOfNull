package controller;

import javafx.fxml.Initializable;

public abstract class Controller implements Initializable
{
    protected MainController mainController;

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }
}
