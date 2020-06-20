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
import services.IUserService;
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
    private IArtistService artistService;
    private IDiscussionService discussionService;

    //pentru apelul prin reflexie
    public ViewArtistProfilePageController() {
        artistService = ServiceProvider.getArtistService();
        discussionService = ServiceProvider.getDiscussionService();
    }

    //pentru testare
    protected ViewArtistProfilePageController(IUserService iUserService, IArtistService iArtistService, IDiscussionService discussionService) {
        super(iUserService);
        artistService = iArtistService;
        this.discussionService = discussionService;
    }


    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.EditArtistProfileContentScene);
    }

    @Override
    protected void onStartChatButtonClick(ActionEvent actionEvent) {
        if (artistModel != null && LoggedUserData.getInstance().isUserLogged()) {
            discussionService.createDiscussion(artistModel.getId(), LoggedUserData.getInstance().getUserModel().getId());
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ChatContentScene, artistModel.getId());
        }
    }

    @Override
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        bandMembersLabel.setVisible(false);
        membersLabel.setVisible(false);

        boolean startChatButtonInvisible = userModel == null || !LoggedUserData.getInstance().isUserLogged()
                || LoggedUserData.getInstance().isRegularUser() || LoggedUserData.getInstance().isArtist();
        startChatButton.setVisible(!startChatButtonInvisible);

        boolean gridInvisible = userModel == null || LoggedUserData.getInstance().isRegularUser() || (LoggedUserData.getInstance().isArtist() && LoggedUserData.getInstance().getUserModel().getId() != userModel.getId());
        scheduleGridController.setVisible(!gridInvisible);

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

        artistModel = artistService.getArtist(userModel.getId());
        updateUIOnInitialize();
    }
}