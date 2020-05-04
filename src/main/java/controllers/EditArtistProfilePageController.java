package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import main.SceneSwitchController;
import javafx.event.ActionEvent;
import models.ArtistModel;
import models.Interval;
import services.ArtistService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @FXML
    public GridPane scheduleGridPane;

    private ArtistService artistService;

    private ArtistModel artistModel;

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
        onSaveChangesButtonClick(actionEvent);
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.MainScene);
    }

    public void onChooseFileButtonClick(ActionEvent actionEvent) {

    }

    public void onSelectBandCheckBoxClick(ActionEvent actionEvent) {

    }

    private List<String> getMembersFromTextArea() {
        String textFromTextArea = bandMembersField.getText();
        return Arrays.asList(textFromTextArea.split(","));
    }

    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (artistModel == null) {
            return;
        }

        List<Interval> intervalsFromGridPane = getIntervalsFromGridPane(scheduleGridPane);

        artistModel.setGenre(genreField.getText());
        artistModel.setName(userNameField.getText());
        artistModel.setIntervals(intervalsFromGridPane);
        artistModel.setMembers(getMembersFromTextArea());

        artistService.updateArtist(artistModel);
    }

    private List<Interval> getIntervalsFromGridPane(GridPane scheduleGridPane) {
        return null;
    }
}
