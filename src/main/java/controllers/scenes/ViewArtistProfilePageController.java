package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import services.IArtistService;
import services.IDiscussionService;
import services.ServiceProvider;

public class ViewArtistProfilePageController extends AbstractViewProfilePageController {
    private ArtistModel artistModel;

    @FXML
    public Label genreLabel;

    @FXML
    public Label bandMembersLabel;

    @FXML
    public Label membersLabel;

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

        boolean startChatButtonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().isArtist();
        startChatButton.setVisible(!startChatButtonInvisible);

        if (artistModel != null) {
            genreLabel.setText(artistModel.getGenre());
            profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));

            if (artistModel.getIs_band()) {
                bandMembersLabel.setVisible(true);
                membersLabel.setVisible(true);
                bandMembersLabel.setText(artistModel.getMembers());
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
