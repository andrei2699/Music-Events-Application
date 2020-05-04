package controllers;

import javafx.event.ActionEvent;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;
import models.UserType;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController extends ChangeableSceneController {

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.MainScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goEditProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditArtistProfileScene);
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
        }
    }
}
