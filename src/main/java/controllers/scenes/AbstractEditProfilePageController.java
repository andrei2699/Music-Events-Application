package controllers.scenes;

import controllers.components.scheduleGrid.EditableScheduleLoadStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.CHOOSE_PROFILE_PICTURE_TEXT;

public abstract class AbstractEditProfilePageController extends AbstractProfilePageController {
    @FXML
    public TextField nameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    protected abstract void onSaveChangesButtonClick(ActionEvent actionEvent);

    protected abstract void onChoosePhotoButtonClick(ActionEvent actionEvent);

    @Override
    public void onSetModelId(Integer modelId) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        scheduleGridController.setLoadStrategy(new EditableScheduleLoadStrategy());
    }

    @Override
    protected void updateUIOnInitialize() {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        nameField.setText(userModel.getName());
        emailField.setText(userModel.getEmail());
        userTypeField.setText(userModel.getType().toString());
    }

    protected final File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(CHOOSE_PROFILE_PICTURE_TEXT);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(SceneSwitchController.getInstance().getPrimaryStage());
    }
}
