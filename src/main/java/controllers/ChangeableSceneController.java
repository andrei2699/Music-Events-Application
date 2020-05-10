package controllers;

import javafx.fxml.Initializable;
import main.SceneSwitchController;

public abstract class ChangeableSceneController implements Initializable {

    public abstract void onSceneChanged();

    public abstract SceneSwitchController.SceneType getControlledSceneType();
}
