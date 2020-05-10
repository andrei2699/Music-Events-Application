package controllers;

import javafx.event.ActionEvent;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;

public class ViewBarProfilePageController extends ViewProfileAbstractController {

    private UserModel userModel;
    private BarModel barModel;

    @Override
    protected void updateUIOnSceneChanged() {
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);
        if (userModel==null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getId() != userModel.getId()){
            editProfilePageButton.setVisible(false);
        }

        nameLabel.setText(userModel.getName());
        userTypeLabel.setText(userModel.getType().toString());
        emailLabel.setText(userModel.getEmail());
        addressLabel.setText(barModel.getAddress());
        profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
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
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        BarService barService = ServiceProvider.getBarService();
        userModel = userService.getUser(userModelId);
        barModel = barService.getBar(userModel.getId());
        updateUIOnSceneChanged();
    }
}
