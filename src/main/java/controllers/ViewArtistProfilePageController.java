package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.ArtistModel;
import models.UserModel;
import services.ArtistService;
import services.BarService;
import services.ServiceProvider;

import java.net.URL;
import java.util.ResourceBundle;

import static main.SceneSwitchController.SceneType.ViewArtistProfileScene;

public class ViewArtistProfilePageController extends ViewProfileAbstractController {
    private UserModel userModel;
    private ArtistModel artistModel;

    @FXML
    public Label genreLabel;

    @FXML
    public Label bandMembersLabel;

    @FXML
    public Label membersLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        bandMembersLabel.setVisible(false);
        membersLabel.setVisible(false);
        updateUIOnSceneChanged();
    }

    @Override
    protected void onEditProfilePageButtonClick(ActionEvent actionEvent) {
        SceneSwitchController.getInstance().switchScene(SceneSwitchController.SceneType.EditArtistProfileScene);
    }

    @Override
    protected void updateUIOnSceneChanged() {
        editProfilePageButton.setOnAction(this::onEditProfilePageButtonClick);
        if (userModel==null || !LoggedUserData.getInstance().isUserLogged() || LoggedUserData.getInstance().getUserModel().getId() != userModel.getId()){
            editProfilePageButton.setVisible(false);
        }
        if(userModel!=null) {
            nameLabel.setText(userModel.getName());
            userTypeLabel.setText(userModel.getType().toString());
            emailLabel.setText(userModel.getEmail());
            genreLabel.setText(artistModel.getGenre());
            profilePhoto.setImage(getProfileImage(artistModel.getPath_to_image()));
            if(artistModel.getIs_band())
            {
                bandMembersLabel.setVisible(true);
                membersLabel.setVisible(true);
                bandMembersLabel.setText(artistModel.getMembers());
            }
        }
    }

    @Override
    public void onSetUserModelId(Integer userModelId) {
        ArtistService artistService = ServiceProvider.getArtistService();
        userModel = userService.getUser(userModelId);
        artistModel = artistService.getArtist(userModel.getId());
        updateUIOnSceneChanged();
    }

    @Override
    public void onSceneChanged() {
    }

    @Override
    public SceneSwitchController.SceneType getControlledSceneType() {
        return ViewArtistProfileScene;
    }
}
