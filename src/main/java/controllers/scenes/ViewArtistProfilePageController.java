package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.SceneSwitchController;
import models.ArtistModel;
import services.ArtistService;
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
    protected void updateUIOnInitialize() {
        super.updateUIOnInitialize();

        bandMembersLabel.setVisible(false);
        membersLabel.setVisible(false);

        if (artistModel != null) {
            genreLabel.setText(artistModel.getGenre());
            profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));

            if (artistModel.getIs_band()) {
                bandMembersLabel.setVisible(true);
                membersLabel.setVisible(true);
                bandMembersLabel.setText(artistModel.getMembers());
            }
        }
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        super.onSetUserModelId(userModelId);

        ArtistService artistService = ServiceProvider.getArtistService();
        artistModel = artistService.getArtist(userModel.getId());
        updateUIOnInitialize();
    }
}
