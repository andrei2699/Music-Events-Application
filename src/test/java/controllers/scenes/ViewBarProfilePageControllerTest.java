package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewController;
import controllers.components.scheduleGrid.ReadonlyScheduleLoadStrategy;
import controllers.components.scheduleGrid.ScheduleGridController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.BarModel;
import models.UserModel;
import models.cards.TableCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IBarService;
import services.IDiscussionService;
import services.IUserService;

import static main.ApplicationResourceStrings.AVAILABLE_EVENTS_TEXT;
import static main.ApplicationResourceStrings.NO_EVENTS_TEXT;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewBarProfilePageControllerTest extends ApplicationTest {
    private static final String VALID_NAME = "Valid Name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email@yahoo.com";

    private ViewBarProfilePageController viewBarProfilePageController;
    private UserModel barUserModel;
    private BarModel barModel;

    @Mock
    private IUserService userService;
    @Mock
    private IBarService barService;
    @Mock
    private IDiscussionService discussionService;

    @Before
    public void setUp() {
        viewBarProfilePageController = new ViewBarProfilePageController(userService, barService, discussionService);
        barUserModel = new UserModel(115, "bar@yahoo.com", "password", "Bar Name", UserType.Manager);
        barModel = new BarModel(115, "Strada Lucian Blaga Nr 20");

        viewBarProfilePageController.nameLabel = new Label();
        viewBarProfilePageController.addressLabel = new Label();
        viewBarProfilePageController.userTypeLabel = new Label();
        viewBarProfilePageController.emailLabel = new Label();
        viewBarProfilePageController.eventsTableViewController = new CardsTableViewController();
        viewBarProfilePageController.eventsTableViewController.cardsTableView = new TableView<>();
        viewBarProfilePageController.eventsTableViewController.cardsTableColumn = new TableColumn<>();
        viewBarProfilePageController.editProfilePageButton = new Button();
        viewBarProfilePageController.startChatButton = new Button();
        viewBarProfilePageController.profilePhoto = new ImageView();
        viewBarProfilePageController.scheduleGridController = new ScheduleGridController();
        viewBarProfilePageController.scheduleGridController.scheduleGridPane = new GridPane();
        viewBarProfilePageController.scheduleGridController.gridVBox = new VBox();
        viewBarProfilePageController.scheduleGridController.setLoadStrategy(new ReadonlyScheduleLoadStrategy());
    }

    @Test
    public void testUpdateUIOnInitializeDiscussionButtonVisible() {
        LoggedUserData.getInstance().setUserModel(new UserModel(79, EMAIL, PASSWORD, VALID_NAME, UserType.Artist));

        when(userService.getUser(115)).thenReturn(barUserModel);
        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.updateUIOnInitialize();

        assertTrue(viewBarProfilePageController.startChatButton.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(534, EMAIL, PASSWORD, VALID_NAME, UserType.RegularUser));
        viewBarProfilePageController.updateUIOnInitialize();
        assertFalse(viewBarProfilePageController.startChatButton.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(34, EMAIL, PASSWORD, VALID_NAME, UserType.Manager));
        viewBarProfilePageController.updateUIOnInitialize();
        assertFalse(viewBarProfilePageController.startChatButton.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeGridVisible() {
        LoggedUserData.getInstance().setUserModel(new UserModel(123, EMAIL, PASSWORD, VALID_NAME, UserType.Artist));

        when(userService.getUser(115)).thenReturn(barUserModel);
        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.updateUIOnInitialize();

        assertTrue(viewBarProfilePageController.scheduleGridController.gridVBox.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(534, EMAIL, PASSWORD, VALID_NAME, UserType.RegularUser));
        viewBarProfilePageController.updateUIOnInitialize();
        assertFalse(viewBarProfilePageController.scheduleGridController.gridVBox.isVisible());

        LoggedUserData.getInstance().setUserModel(new UserModel(34, EMAIL, PASSWORD, VALID_NAME, UserType.Manager));
        viewBarProfilePageController.updateUIOnInitialize();
        assertFalse(viewBarProfilePageController.scheduleGridController.gridVBox.isVisible());
    }

    @Test
    public void testUpdateUIOnInitializeNullBarModel() {
        when(userService.getUser(115)).thenReturn(barUserModel);
        when(barService.getBar(115)).thenReturn(null);

        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.updateUIOnInitialize();

        assertTrue(viewBarProfilePageController.addressLabel.getText().isEmpty());
        assertEquals("Bar Name", viewBarProfilePageController.nameLabel.getText());
        assertEquals(barUserModel.getEmail(), viewBarProfilePageController.emailLabel.getText());
    }

    @Test
    public void testUpdateUIOnInitializeValidBarModel() {
        when(userService.getUser(115)).thenReturn(barUserModel);
        when(barService.getBar(115)).thenReturn(barModel);


        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.updateUIOnInitialize();

        assertEquals(barUserModel.getName(), viewBarProfilePageController.nameLabel.getText());
        assertEquals(barUserModel.getEmail(), viewBarProfilePageController.emailLabel.getText());
        assertEquals(barUserModel.getType().toString(), viewBarProfilePageController.userTypeLabel.getText());
        assertEquals(barModel.getAddress(), viewBarProfilePageController.addressLabel.getText());
    }

    @Test
    public void testInitialize() {
        viewBarProfilePageController.initialize(null, null);

        assertEquals(AVAILABLE_EVENTS_TEXT, viewBarProfilePageController.eventsTableViewController.cardsTableColumn.getText());
        assertEquals(NO_EVENTS_TEXT, ((Label) viewBarProfilePageController.eventsTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals("eventCardModel", ((PropertyValueFactory<TableCardModel, TableCardModel>)
                viewBarProfilePageController.eventsTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
    }

    @Test
    public void testOnStartChatButtonClick() {
        when(userService.getUser(115)).thenReturn(barUserModel);
        when(barService.getBar(115)).thenReturn(barModel);
        LoggedUserData.getInstance().setUserModel(null);

        viewBarProfilePageController.initialize(null, null);
        viewBarProfilePageController.onSetModelId(115);
        try {
            viewBarProfilePageController.onStartChatButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        LoggedUserData.getInstance().setUserModel(new UserModel(32, "artist@yahoo.com", "password", "Artist Name", UserType.Artist));
        when(barService.getBar(115)).thenReturn(null);

        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.initialize(null, null);
        try {
            viewBarProfilePageController.onStartChatButtonClick(null);
        } catch (NullPointerException e) {
            fail();
        }

        when(barService.getBar(115)).thenReturn(barModel);

        viewBarProfilePageController.onSetModelId(115);
        viewBarProfilePageController.initialize(null, null);
        try {
            viewBarProfilePageController.onStartChatButtonClick(null);
        } catch (Exception e) {
            fail();
        }
    }
}