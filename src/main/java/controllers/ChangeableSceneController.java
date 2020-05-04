package controllers;

import javafx.fxml.Initializable;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ChangeableSceneController implements Initializable {
    public abstract void onSceneChanged();

    public abstract SceneSwitchController.SceneType getControlledSceneType();
}
