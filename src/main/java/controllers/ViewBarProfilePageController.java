package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewBarProfilePageController extends ViewProfileAbstractController {

    private BarModel barModel;

    @FXML
    public Label addressLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);

        updateUIOnSceneChanged();
    }

    @Override
    protected void updateUIOnSceneChanged() {
        super.updateUIOnSceneChanged();

        if (userModel != null && barModel != null) {
            nameLabel.setText(userModel.getName());
            userTypeLabel.setText(userModel.getType().toString());
            emailLabel.setText(userModel.getEmail());
            addressLabel.setText(barModel.getAddress());
            profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));

            eventsTableView.setItems(getAllFutureEventsLinkedWithId(userModel.getId()));
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
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditBarProfileScene);
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        super.onSetUserModelId(userModelId);

        BarService barService = ServiceProvider.getBarService();
        barModel = barService.getBar(userModel.getId());
        updateUIOnSceneChanged();
    }
}
