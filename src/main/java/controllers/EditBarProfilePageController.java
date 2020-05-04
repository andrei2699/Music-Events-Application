package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.Interval;
import models.UserModel;
import services.BarService;
import services.ServiceProvider;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditBarProfilePageController extends EditProfileAbstractController {

    @FXML
    public TextField addressField;

    private BarService barService;

    private BarModel barModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        barService = ServiceProvider.getBarService();

        if (LoggedUserData.getInstance().isUserLogged()) {
            UserModel userModel = LoggedUserData.getInstance().getUserModel();
            barModel = barService.getBar(userModel.getId());
            if (barModel == null) {
                barModel = new BarModel(userModel.getId(), "");
            }
            barService.createBar(barModel);
        }
    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.EditBarProfileScene;
    }

    @Override
    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {
        File selectedFile = openFileChooser();
        if (selectedFile != null) {
            barModel.setPath_to_image(selectedFile.getPath());
            profilePhoto.setImage(getProfileImage(selectedFile.getPath()));

            barService.updateBar(barModel);
        }
    }

    @Override
    protected void updateUIOnSceneChanged() {
        fillFieldsWithValuesFromLoggedUserData();
        List<Interval> intervals;
        if (barModel == null) {
            intervals = null;
        } else {
            intervals = barModel.getIntervals();
        }

        gridHBoxes = fillScheduleGridPane(scheduleGridPane, intervals);
    }

    @Override
    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (barModel == null) {
            return;
        }

        List<Interval> intervalsFromGridPane = getIntervalsFromGrid(gridHBoxes);
        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        barModel.setAddress(addressField.getText());
        if (!nameField.getText().isEmpty()) {
            userModel.setName(nameField.getText());
        }
        barModel.setIntervals(intervalsFromGridPane);

        barService.updateBar(barModel);
        userService.updateUser(userModel);
    }

    @Override
    protected void fillFieldsWithValuesFromLoggedUserData() {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        nameField.setText(userModel.getName());
        emailField.setText(userModel.getEmail());
        userTypeField.setText(userModel.getType().toString());
        BarModel barModel = barService.getBar(userModel.getId());
        if (barModel == null) {
            return;
        }

        addressField.setText(barModel.getAddress());
        profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
    }
}
