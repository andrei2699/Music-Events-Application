package controllers;

import javafx.fxml.Initializable;
import main.SceneSwitchController;

public abstract class ChangeableSceneController implements Initializable {

    protected Integer userModelId;

    public void setUserModelId(Integer userModelId) {
        this.userModelId = userModelId;
    }

    public abstract void onSceneChanged();

    public abstract SceneSwitchController.SceneType getControlledSceneType();
}
