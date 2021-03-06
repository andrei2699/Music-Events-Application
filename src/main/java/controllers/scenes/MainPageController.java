package controllers.scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.UserModel;
import models.other.UserType;
import services.IDiscussionService;
import services.ServiceProvider;

import java.net.URL;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.*;

public class MainPageController implements Initializable {
    @FXML
    public AnchorPane pageContent;

    @FXML
    public ImageView moreActionsImage;

    @FXML
    public ImageView messageImage;

    protected ContextMenu moreActionsContextMenu;

    private final IDiscussionService discussionService;

    protected final Image noMessages = new Image(IMAGE_FOR_NO_MESSAGES_PATH);
    protected final Image newMessages = new Image(IMAGE_FOR_NEW_MESSAGES_PATH);

    // for reflexion call
    public MainPageController() {
        this(ServiceProvider.getDiscussionService());
    }

    // for testing
    protected MainPageController(IDiscussionService discussionService) {
        this.discussionService = discussionService;
    }

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

        if (LoggedUserData.getInstance().isUserLogged()) {
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

        setMessageImage();
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
        } else {
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ViewRegularUserContentScene);
        }
    }

    public void onMessageClick(MouseEvent mouseEvent) {
        UserModel user = LoggedUserData.getInstance().getUserModel();
        if (user != null) {
            messageImage.setImage(noMessages);
            SceneSwitchController.getInstance().loadFXMLToMainPage(SceneSwitchController.SceneType.ChatContentScene, user.getId());
        }
    }

    private void setMessageImage() {
        if (!LoggedUserData.getInstance().isBarManager() && !LoggedUserData.getInstance().isArtist()) {
            messageImage.setVisible(false);
            return;
        }
        UserModel user = LoggedUserData.getInstance().getUserModel();
        if (discussionService.checkNewMessage(user.getId()))
            messageImage.setImage(newMessages);
        else
            messageImage.setImage(noMessages);
    }
}