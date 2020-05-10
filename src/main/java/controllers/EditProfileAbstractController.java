package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import main.SceneSwitchController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class EditProfileAbstractController extends AbstractProfilePageController {
    @FXML
    public TextField nameField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    protected abstract void onSaveChangesButtonClick(ActionEvent actionEvent);

    protected abstract void fillFieldsWithValuesFromLoggedUserData();

    protected abstract void onChoosePhotoButtonClick(ActionEvent actionEvent);

    @Override
    public void onSetUserModelId(Integer userModelId) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        updateUIOnSceneChanged();
    }

    @Override
    public void onSceneChanged() {
        // make a save to the database to create the bar record
//        onSaveChangesButtonClick(null);
//
//        Task<Void> sleeper = new Task<>() {
//            @Override
//            protected Void call() {
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ignored) {
//                }
//                return null;
//            }
//        };
//        sleeper.setOnSucceeded(event -> updateUIOnSceneChanged());
//        new Thread(sleeper).start();
        updateUIOnSceneChanged();
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {
        onSaveChangesButtonClick(actionEvent);
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }

    protected File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege poza profil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(SceneSwitchController.getInstance().getPrimaryStage());
    }
}
