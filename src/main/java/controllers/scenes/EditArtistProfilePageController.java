package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import models.other.Interval;
import models.UserModel;
import services.IArtistService;
import services.ServiceProvider;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditArtistProfilePageController extends AbstractEditProfilePageController {

    @FXML
    public TextField genreField;

    @FXML
    public TextArea bandMembersField;

    @FXML
    public Label bandMembersLabel;

    @FXML
    public CheckBox bandCheckBox;

    private IArtistService artistService;

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

            updateUIOnInitialize();
        }
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
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        if (artistModel != null) {
            bandMembersField.setText(artistModel.getMembers());
            bandCheckBox.setSelected(artistModel.isIs_band());
            onSelectBandCheckBoxClick(null);

            genreField.setText(artistModel.getGenre());
            profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));

            List<Interval> intervals = artistModel.getIntervals();

            gridHBoxes = fillScheduleGridPane(scheduleGridPane, intervals);
        }
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

        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ViewArtistProfileContentScene, artistModel.getId());
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
