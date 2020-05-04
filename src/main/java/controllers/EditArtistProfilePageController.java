package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import main.SceneSwitchController;

import java.net.URL;
import java.util.ResourceBundle;

public class EditArtistProfilePageController extends ChangeableSceneController {
    @FXML
    public ImageView profilePhoto;

    @FXML
    public TextField userNameField;

    @FXML
    public ChoiceBox chooseArtistType;

    @FXML
    public GridPane scheduleGridArtist;

    @FXML
    public TextField emailField;

    @FXML
    public TextField userTypeField;

    @FXML
    public TextField genreField;

    @FXML
    public TextArea bandMembersField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
