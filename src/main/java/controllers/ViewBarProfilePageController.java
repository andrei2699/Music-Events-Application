package controllers;

import javafx.event.ActionEvent;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;

public class ViewBarProfilePageController extends ViewProfileAbstractController {

    private UserModel userModel;

    @Override
    protected void updateUIOnSceneChanged() {
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);
        if (userModel == null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getId() != userModel.getId()) {
            // ascunde butonul
        }
    }

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.ViewBarProfileScene;
    }

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {

    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        userModel = userService.getUser(userModelId);
        updateUIOnSceneChanged();
    }
}
