package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;
import models.other.UserType;

import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.*;

public class MainPageController implements Initializable {
    @FXML
    public AnchorPane pageContent;

    @FXML
    public ImageView moreActionsImage;

    private ContextMenu moreActionsContextMenu;

    @FXML
    public void onMoreActionsClicked(MouseEvent mouseEvent) {
        Bounds bounds = moreActionsImage.localToScreen(moreActionsImage.getBoundsInLocal());
        double x = bounds.getMaxX();
        double y = bounds.getMaxY();
        moreActionsContextMenu.show(moreActionsImage, x, y);
        moreActionsContextMenu.setX(x - moreActionsContextMenu.getWidth());
    }

    @FXML
    public void onTitleClick(MouseEvent mouseEvent) {
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.MainSceneContent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moreActionsContextMenu = new ContextMenu();

        SceneSwitchController.getInstance().setMainPageContentPane(pageContent);
        SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.MainSceneContent);

        if (LoggedUserData.getInstance().isBarManager() || LoggedUserData.getInstance().isArtist()) {
            MenuItem goToProfileMenuItem = new MenuItem(VIEW_PROFILE_TEXT);
            goToProfileMenuItem.setOnAction(this::goViewProfile);
            moreActionsContextMenu.getItems().add(goToProfileMenuItem);
        }

        if (LoggedUserData.getInstance().isBarManager()) {
            MenuItem goToCreateEventForm = new MenuItem(CREATE_EVENT_TEXT);
            goToCreateEventForm.setOnAction(event -> SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.CreateEventFormContentScene));
            moreActionsContextMenu.getItems().add(goToCreateEventForm);
        }

        MenuItem goToMainPageContent = new MenuItem(MAIN_PAGE_TEXT);
        goToMainPageContent.setOnAction((actionEvent) -> onTitleClick(null));
        moreActionsContextMenu.getItems().add(goToMainPageContent);

        MenuItem goToLogin;
        if (LoggedUserData.getInstance().isUserLogged()) {
            goToLogin = new MenuItem(LOG_OUT_TEXT);
        } else {
            goToLogin = new MenuItem(LOG_IN_TEXT);
        }

        goToLogin.setOnAction(event -> SceneSwitchController.getInstance().switchToLoginScene());

        moreActionsContextMenu.getItems().add(goToLogin);
    }

    private void goViewProfile(ActionEvent actionEvent) {
        if (!LoggedUserData.getInstance().isUserLogged()) {
            return;
        }

        UserModel userModel = LoggedUserData.getInstance().getUserModel();
        if (userModel.getType() == UserType.Artist) {
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ViewArtistProfileContentScene, LoggedUserData.getInstance().getUserModel().getId());
        } else if (userModel.getType() == UserType.Manager) {
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ViewBarProfileContentScene, LoggedUserData.getInstance().getUserModel().getId());
        }
    }
}
