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
import static main.ApplicationResourceStrings.CHOOSE_VIDEO_TEXT;

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

    protected final File openImageFileChooser() {
        return openFileChooser(CHOOSE_PROFILE_PICTURE_TEXT, "Image Files", "*.png", "*.jpg", "*.gif");
    }

    protected final File openVideoFileChooser() {
        return openFileChooser(CHOOSE_VIDEO_TEXT, "Video Files", "*.mp4");
    }

    private File openFileChooser(String title, String description, String... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        return fileChooser.showOpenDialog(SceneSwitchController.getInstance().getPrimaryStage());
    }
}
