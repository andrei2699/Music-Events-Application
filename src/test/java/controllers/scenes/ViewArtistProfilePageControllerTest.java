package controllers.scenes;

import controllers.components.VideoPlayerComponentController;
import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.scheduleGrid.ReadonlyScheduleLoadStrategy;
import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.ArtistModel;
import models.UserModel;
import models.cards.TableCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IArtistService;
import services.IDiscussionService;
import services.IUserService;

import static main.ApplicationResourceStrings.AVAILABLE_EVENTS_TEXT;
import static main.ApplicationResourceStrings.NO_EVENTS_TEXT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewArtistProfilePageControllerTest extends ApplicationTest {
    private static final String VALID_NAME = "Valid Name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@yahoo.com";

    private ViewArtistProfilePageController viewArtistProfilePageController;
    private UserModel artistUserModel;
    private ArtistModel artistModel;

    @Mock
    private IUserService userService;
    @Mock
    private IArtistService artistService;
    @Mock
    private IDiscussionService discussionService;

    @Before
    public void setUp() {
        viewArtistProfilePageController = new ViewArtistProfilePageController(userService, artistService, discussionService);
        artistUserModel = new UserModel(43, "artist@yahoo.com", "password", "Artist Name", UserType.Artist);
        artistModel = new ArtistModel(43, false, "Rock n Roll");

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
        viewArtistProfilePageController.eventsTableViewController.cardsTableView = new TableView<>();
        viewArtistProfilePageController.eventsTableViewController.cardsTableColumn = new TableColumn<>();
        viewArtistProfilePageController.editProfilePageButton = new Button();
        viewArtistProfilePageController.startChatButton = new Button();
        viewArtistProfilePageController.profilePhoto = new ImageView();
        viewArtistProfilePageController.scheduleGridController = new ScheduleGridController();
        viewArtistProfilePageController.scheduleGridController.scheduleGridPane = new GridPane();
        viewArtistProfilePageController.scheduleGridController.gridVBox = new VBox();
        viewArtistProfilePageController.scheduleGridController.setLoadStrategy(new ReadonlyScheduleLoadStrategy());
    }

    @Test
    public void testUpdateUIOnInitializeDiscussionButtonVisible() {
        LoggedUserData.getInstance().setUserModel(new UserModel(123, EMAIL, PASSWORD, VALID_NAME, UserType.Manager));

        when(userService.getUser(43)).thenReturn(artistUserModel);
        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.startChatButton.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeGridVisible() {
        LoggedUserData.getInstance().setUserModel(new UserModel(123, EMAIL, PASSWORD, VALID_NAME, UserType.Manager));

        when(userService.getUser(43)).thenReturn(artistUserModel);
        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.scheduleGridController.gridVBox.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(534, EMAIL, PASSWORD, VALID_NAME, UserType.RegularUser));
        viewArtistProfilePageController.updateUIOnInitialize();
        assertFalse(viewArtistProfilePageController.scheduleGridController.gridVBox.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(34, EMAIL, PASSWORD, VALID_NAME, UserType.Artist));
        viewArtistProfilePageController.updateUIOnInitialize();
        assertFalse(viewArtistProfilePageController.scheduleGridController.gridVBox.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeNullArtistModel() {
        when(userService.getUser(43)).thenReturn(artistUserModel);
        when(artistService.getArtist(43)).thenReturn(null);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertTrue(viewArtistProfilePageController.genreLabel.getText().isEmpty());
        assertTrue(viewArtistProfilePageController.bandMembersLabel.getText().isEmpty());
        assertEquals("Artist Name", viewArtistProfilePageController.nameLabel.getText());
        assertEquals(artistUserModel.getEmail(), viewArtistProfilePageController.emailLabel.getText());
    }

    @Test
    public void testUpdateUIOnInitializeSoloArtist() {
        when(userService.getUser(43)).thenReturn(artistUserModel);
        when(artistService.getArtist(43)).thenReturn(artistModel);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertEquals(artistModel.getGenre(), viewArtistProfilePageController.genreLabel.getText());
        assertEquals(artistModel.getMembers(), viewArtistProfilePageController.bandMembersLabel.getText());
        assertEquals(artistUserModel.getName(), viewArtistProfilePageController.nameLabel.getText());
        assertEquals(artistUserModel.getEmail(), viewArtistProfilePageController.emailLabel.getText());
        assertEquals(artistUserModel.getType().toString(), viewArtistProfilePageController.userTypeLabel.getText());

        assertFalse(viewArtistProfilePageController.detailsTabPane.getTabs().contains(viewArtistProfilePageController.videoTab));
        assertFalse(viewArtistProfilePageController.bandMembersLabel.isVisible());
        assertFalse(viewArtistProfilePageController.membersLabel.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeBand() {
        artistModel.setIs_band(true);

        LoggedUserData.getInstance().setUserModel(new UserModel(43, EMAIL, PASSWORD, VALID_NAME, UserType.Manager));
        when(userService.getUser(43)).thenReturn(artistUserModel);
        when(artistService.getArtist(43)).thenReturn(artistModel);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertEquals(artistModel.getGenre(), viewArtistProfilePageController.genreLabel.getText());
        assertEquals(artistModel.getMembers(), viewArtistProfilePageController.bandMembersLabel.getText());
        assertEquals(artistUserModel.getName(), viewArtistProfilePageController.nameLabel.getText());
        assertEquals(artistUserModel.getEmail(), viewArtistProfilePageController.emailLabel.getText());
        assertEquals(artistUserModel.getType().toString(), viewArtistProfilePageController.userTypeLabel.getText());

        assertFalse(viewArtistProfilePageController.detailsTabPane.getTabs().contains(viewArtistProfilePageController.videoTab));
        assertTrue(viewArtistProfilePageController.bandMembersLabel.isVisible());
        assertTrue(viewArtistProfilePageController.membersLabel.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeValidArtistModel() {
        when(userService.getUser(43)).thenReturn(artistUserModel);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.updateUIOnInitialize();

        assertEquals(artistUserModel.getName(), viewArtistProfilePageController.nameLabel.getText());
    }

    @Test
    public void testInitialize() {
        viewArtistProfilePageController.initialize(null, null);

        assertEquals(AVAILABLE_EVENTS_TEXT, viewArtistProfilePageController.eventsTableViewController.cardsTableColumn.getText());
        assertEquals(NO_EVENTS_TEXT, ((Label) viewArtistProfilePageController.eventsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals("eventCardModel", ((PropertyValueFactory<TableCardModel, TableCardModel>)
                viewArtistProfilePageController.eventsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
    }

    @Test
    public void testOnStartChatButtonClick() {
        when(userService.getUser(43)).thenReturn(artistUserModel);
        when(artistService.getArtist(43)).thenReturn(artistModel);
        LoggedUserData.getInstance().setUserModel(null);

        viewArtistProfilePageController.initialize(null, null);
        viewArtistProfilePageController.onSetModelId(43);
        try {
            viewArtistProfilePageController.onStartChatButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        LoggedUserData.getInstance().setUserModel(new UserModel(2, "email@gg.com", "pass", "My Name", UserType.Manager));
        when(artistService.getArtist(43)).thenReturn(null);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.initialize(null, null);
        try {
            viewArtistProfilePageController.onStartChatButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        when(artistService.getArtist(43)).thenReturn(artistModel);

        viewArtistProfilePageController.onSetModelId(43);
        viewArtistProfilePageController.initialize(null, null);
        try {
            viewArtistProfilePageController.onStartChatButtonClick(null);
        } catch (Exception e) {
            fail();
        }
    }
}