package controllers.scenes;

import controllers.components.VideoPlayerComponentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import models.UserModel;
import models.other.Interval;
import services.IArtistService;
import services.IUserService;
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
    public VideoPlayerComponentController videoPlayerComponentController;

    private IArtistService artistService;
    private ArtistModel artistModel;

    //pentru apelul prin reflexie
    public EditArtistProfilePageController() {
        artistService = ServiceProvider.getArtistService();
    }

    //pentru testare
    protected EditArtistProfilePageController(IUserService iUserService, IArtistService iArtistService) {
        super(iUserService);
        artistService = iArtistService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        if (LoggedUserData.getInstance().isUserLogged()) {
            UserModel userModel = LoggedUserData.getInstance().getUserModel();
            artistModel = artistService.getArtist(userModel.getId());
            if (artistModel == null) {
                artistModel = new ArtistModel(userModel.getId(), false, "");
            }
            artistService.createArtist(artistModel);

            updateUIOnInitialize();
        }

        SceneSwitchController.getInstance().addOnSceneSwitchListener(videoPlayerComponentController::stopVideo);
    }

    @Override
    public void onChoosePhotoButtonClick(ActionEvent actionEvent) {
        bandMembersLabel.requestFocus();
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
            videoPlayerComponentController.videoMediaView.requestFocus();
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
        if (userModel == null) {
            return;
        }

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
        videoPlayerComponentController.setVideo(artistModel.getPath_to_video());
    }
}
