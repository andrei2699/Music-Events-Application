package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;

import java.io.File;

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
    public void onSetUserModelId(Integer userModelId) {
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

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {
        onSaveChangesButtonClick(actionEvent);
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }

    protected final File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege poza profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(SceneSwitchController.getInstance().getPrimaryStage());
    }
}
