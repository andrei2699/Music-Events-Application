package controllers.scenes;

import javafx.fxml.Initializable;

public abstract class ChangeableSceneWithModelController implements Initializable {
    public abstract void onSetModelId(Integer modelId);
}
