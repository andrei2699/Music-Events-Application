package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import models.UserModel;
import models.other.Interval;
import services.IArtistService;
import services.ServiceProvider;
import utils.StringValidator;

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

    @FXML
    public MediaView videoMediaView;

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
        File selectedFile = openImageFileChooser();
        if (selectedFile != null) {
            artistModel.setPath_to_image(selectedFile.getPath());
            profilePhoto.setImage(getProfileImage(selectedFile.getPath()));

            artistService.updateArtist(artistModel);
        }
    }

    public void onChooseVideoButtonClick(ActionEvent actionEvent) {
        File file = openVideoFileChooser();
        if (file != null) {
            artistModel.setPath_to_video(file.getPath());
            setVideo();
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

            if (StringValidator.isStringNotEmpty(artistModel.getPath_to_video())) {
                setVideo();
            }

            List<Interval> intervals = artistModel.getIntervals();

            scheduleGridController.setIntervals(intervals);
        }
    }

    @Override
    public void onSaveChangesButtonClick(ActionEvent actionEvent) {
        if (artistModel == null) {
            return;
        }

        List<Interval> intervalsFromGridPane = scheduleGridController.getIntervalsFromGrid();
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

    private void setVideo() {
        Media media = new Media(artistModel.getPath_to_video());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);

        videoMediaView.setMediaPlayer(mediaPlayer);
    }
}
