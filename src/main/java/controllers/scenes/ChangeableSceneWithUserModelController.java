package controllers.scenes;

import javafx.fxml.Initializable;

public abstract class ChangeableSceneWithUserModelController implements Initializable {
    public abstract void onSetUserModelId(Integer userModelId);
}
