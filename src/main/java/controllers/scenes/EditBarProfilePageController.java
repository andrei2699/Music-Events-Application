package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.UserModel;
import models.other.Interval;
import services.IBarService;
import services.ServiceProvider;
import utils.StringValidator;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditBarProfilePageController extends AbstractEditProfilePageController {

    @FXML
    public TextField addressField;

    @FXML
    public Label requiredAdreesErrorLabel;

    private IBarService barService;

    private BarModel barModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        requiredAdreesErrorLabel.setVisible(false);

        barService = ServiceProvider.getBarService();

        if (LoggedUserData.getInstance().isUserLogged()) {
            UserModel userModel = LoggedUserData.getInstance().getUserModel();
            barModel = barService.getBar(userModel.getId());
            if (barModel == null) {
                barModel = new BarModel(userModel.getId(), "");
            }
            barService.createBar(barModel);

            updateUIOnInitialize();
        }
    }

    @Override
    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {
        requiredAdreesErrorLabel.requestFocus();
        File selectedFile = openImageFileChooser();
        if (selectedFile != null) {
            barModel.setPath_to_image(selectedFile.getPath());
            profilePhoto.setImage(getProfileImage(selectedFile.getPath()));
            barService.updateBar(barModel);
        }
    }

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        if (barModel != null) {
            addressField.setText(barModel.getAddress());
            profilePhoto.setImage(getProfileImage(barModel.getPath_to_image()));
            List<Interval> intervals = barModel.getIntervals();

            scheduleGridController.setIntervals(intervals);
        }
    }

    @Override
    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (barModel == null) {
            return;
        }

        if (StringValidator.isStringEmpty(addressField.getText())) {
            showErrorLabel(requiredAdreesErrorLabel);
            addressField.requestFocus();
        } else {
            List<Interval> intervalsFromGridPane = scheduleGridController.getIntervalsFromGrid();
            UserModel userModel = LoggedUserData.getInstance().getUserModel();

            barModel.setAddress(addressField.getText());
            if (!nameField.getText().isEmpty()) {
                userModel.setName(nameField.getText());
            }
            barModel.setIntervals(intervalsFromGridPane);

            barService.updateBar(barModel);
            userService.updateUser(userModel);

            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ViewBarProfileContentScene, barModel.getId());
        }
    }

    private void showErrorLabel(Label label) {
        label.setVisible(true);
    }
}
