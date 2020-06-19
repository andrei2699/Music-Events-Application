package controllers.scenes;

import controllers.components.VideoPlayerComponentController;
import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import main.LoggedUserData;
import models.ArtistModel;
import models.BarModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IArtistService;
import services.IUserService;

import static main.ApplicationResourceStrings.IMAGE_DEFAULT_USER_PHOTO_PATH;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewArtistProfilePageControllerTest extends ApplicationTest {
    private static final String VALID_NAME = "Valid Name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@yahoo.com";

    ViewArtistProfilePageController viewArtistProfilePageController;
    UserModel artistModel;

    @Mock
    private IUserService userService;
    @Mock
    private IArtistService artistService;
    @Before
    public void setUp() {
        viewArtistProfilePageController = new ViewArtistProfilePageController(userService,artistService);
        artistModel = new UserModel(43,"artist@yahoo.com","password","Artist Name",UserType.Artist);

        viewArtistProfilePageController.detailsTabPane = new TabPane();
        viewArtistProfilePageController.videoTab = new Tab();
        viewArtistProfilePageController.videoPlayerComponentController = new VideoPlayerComponentController();
        viewArtistProfilePageController.bandMembersLabel = new Label();
        viewArtistProfilePageController.nameLabel = new Label();
        viewArtistProfilePageController.genreLabel = new Label();
        viewArtistProfilePageController.membersLabel = new Label();
        viewArtistProfilePageController.userTypeLabel = new Label();
        viewArtistProfilePageController.emailLabel = new Label();
        viewArtistProfilePageController.eventsTableViewController = new CardsTableViewController();
        viewArtistProfilePageController.editProfilePageButton = new Button();
        viewArtistProfilePageController.startChatButton = new Button();
        viewArtistProfilePageController.profilePhoto = new ImageView();
        viewArtistProfilePageController.scheduleGridController = new ScheduleGridController();
    }

    @Test
    public void testUpdateUIOnInitializeDiscussionButtonVisible() {
        LoggedUserData.getInstance().setUserModel(new UserModel(123,EMAIL,PASSWORD,VALID_NAME, UserType.Manager));

        when(userService.getUser(43)).thenReturn(artistModel);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.startChatButton.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeNullArtistModel() {
        when(userService.getUser(43)).thenReturn(null);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.genreLabel.getText().isEmpty());
        assertTrue(viewArtistProfilePageController.bandMembersLabel.getText().isEmpty());
        assertTrue(viewArtistProfilePageController.nameLabel.getText().isEmpty());
    }

    @Test
    public void testUpdateUIOnInitialize() {
        when(userService.getUser(43)).thenReturn(null);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.genreLabel.getText().isEmpty());
        assertTrue(viewArtistProfilePageController.bandMembersLabel.getText().isEmpty());
        assertTrue(viewArtistProfilePageController.nameLabel.getText().isEmpty());
    }

    @Test
    public void testUpdateUIOnInitializeValidArtistModel() {
        when(userService.getUser(43)).thenReturn(artistModel);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertEquals(artistModel.getName(),viewArtistProfilePageController.nameLabel.getText());
    }
}