package controllers;

import javafx.event.ActionEvent;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class EditArtistProfilePageController extends ChangeableSceneController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @Override
    public void onSceneChanged() {

    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.EditArtistProfileScene;
    }

    public void onGoToStartPageButtonClick(ActionEvent actionEvent) {

    }

    public void onChooseFileButtonClick(ActionEvent actionEvent) {

    }

    public void onSelectBandCheckBoxClick(ActionEvent actionEvent) {

    }

    public void onSaveChangesButtonClick(ActionEvent actionEvent) {

    }
}
