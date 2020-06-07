package controllers.scenes;

import controllers.components.VideoPlayerComponentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import services.IArtistService;
import services.IDiscussionService;
import services.ServiceProvider;
import utils.StringValidator;

public class ViewArtistProfilePageController extends AbstractViewProfilePageController {
    @FXML
    public TabPane detailsTabPane;

    @FXML
    public Tab videoTab;

    @FXML
    public VideoPlayerComponentController videoPlayerComponentController;

    @FXML
    public Label genreLabel;

    @FXML
    public Label bandMembersLabel;

    @FXML
    public Label membersLabel;

    private ArtistModel artistModel;

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.EditArtistProfileContentScene);
    }

    @Override
    protected void onStartChatButtonClick(ActionEvent actionEvent) {
        IDiscussionService discussionService = ServiceProvider.getDiscussionService();
        discussionService.createDiscussion(artistModel.getId(), LoggedUserData.getInstance().getUserModel().getId());
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ChatContentScene, artistModel.getId());
    }

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        bandMembersLabel.setVisible(false);
        membersLabel.setVisible(false);

        boolean startChatButtonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged()
                || LoggedUserData.getInstance().isRegularUser() || LoggedUserData.getInstance().isArtist();
        startChatButton.setVisible(!startChatButtonInvisible);

        if (artistModel != null) {
            genreLabel.setText(artistModel.getGenre());
            profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));

            if (artistModel.getIs_band()) {
                bandMembersLabel.setVisible(true);
                membersLabel.setVisible(true);
                bandMembersLabel.setText(artistModel.getMembers());
            }

            if (StringValidator.isStringEmpty(artistModel.getPath_to_video())) {
                detailsTabPane.getTabs().remove(videoTab);
            } else {
                videoPlayerComponentController.setVideo(artistModel.getPath_to_video());
            }

            scheduleGridController.setIntervals(artistModel.getIntervals());
        }
    }

    @Override
    public void onSetModelId(Integer modelId) {
        super.onSetModelId(modelId);

        IArtistService artistService = ServiceProvider.getArtistService();
        artistModel = artistService.getArtist(userModel.getId());
        updateUIOnInitialize();
    }
}
