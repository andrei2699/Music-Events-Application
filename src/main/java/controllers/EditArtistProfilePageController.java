package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import models.Interval;
import models.UserModel;
import services.ServiceProvider;
import services.interfaces.ArtistService;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditArtistProfilePageController extends EditProfileAbstractController {

    @FXML
    public TextField genreField;

    @FXML
    public TextArea bandMembersField;

    @FXML
    public Label bandMembersLabel;

    @FXML
    public CheckBox bandCheckBox;

    private ArtistService artistService;

    private ArtistModel artistModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        artistService = ServiceProvider.getArtistService();

        if (LoggedUserData.getInstance().isUserLogged()) {
            UserModel userModel = LoggedUserData.getInstance().getUserModel();
            artistModel = artistService.getArtist(userModel.getId());
            if (artistModel == null) {
                artistModel = new ArtistModel(userModel.getId(), false, "");
            }
            artistService.createArtist(artistModel);
        }
    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return SceneSwitchController.SceneType.EditArtistProfileScene;
    }

    @Override
    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {

        File selectedFile = openFileChooser();
        if (selectedFile != null) {
            artistModel.setPath_to_image(selectedFile.getPath());
            profilePhoto.setImage(getProfileImage(selectedFile.getPath()));

            artistService.updateArtist(artistModel);
        }
    }

    @Override
    protected void updateUIOnSceneChanged() {
        fillFieldsWithValuesFromLoggedUserData();
        List<Interval> intervals;
        if (artistModel == null) {
            intervals = null;
        } else {
            intervals = artistModel.getIntervals();
        }

        gridHBoxes = fillScheduleGridPane(scheduleGridPane, intervals);
    }

    @Override
    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (artistModel == null) {
            return;
        }

        List<Interval> intervalsFromGridPane = getIntervalsFromGrid(gridHBoxes);
        UserModel userModel = LoggedUserData.getInstance().getUserModel();

        artistModel.setGenre(genreField.getText());
        if (!nameField.getText().isEmpty()) {
            userModel.setName(nameField.getText());
        }
        artistModel.setIntervals(intervalsFromGridPane);
        artistModel.setMembers(bandMembersField.getText());
        artistModel.setIs_band(bandCheckBox.isSelected());

        artistService.updateArtist(artistModel);
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
        ArtistModel artistModel = artistService.getArtist(userModel.getId());
        if (artistModel == null) {
            return;
        }

        bandMembersField.setText(artistModel.getMembers());
        bandCheckBox.setSelected(artistModel.isIs_band());
        onSelectBandCheckBoxClick(null);

        genreField.setText(artistModel.getGenre());
        profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));
    }

    public void onSelectBandCheckBoxClick(ActionEvent actionEvent) {
        if (bandCheckBox.isSelected()) {
            bandMembersLabel.setVisible(true);
            bandMembersField.setVisible(true);
        } else {
            bandMembersLabel.setVisible(false);
            bandMembersField.setVisible(false);
        }
    }
}
