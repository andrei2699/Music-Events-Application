package controllers.scenes;

import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.LoggedUserData;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IDiscussionService;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainPageControllerTest extends ApplicationTest {
    private static final double ERROR = 0.01;

    private MainPageController mainPageController;

    @Mock
    private IDiscussionService discussionService;

    @Before
    public void setUp() {
        mainPageController = new MainPageController(discussionService);

        mainPageController.pageContent = new AnchorPane();
        mainPageController.messageImage = new ImageView();
        mainPageController.moreActionsImage = new ImageView();
        mainPageController.moreActionsContextMenu = new ContextMenu();
    }

    @Test
    public void testInitializeNoLoggedUser() {
        LoggedUserData.getInstance().setUserModel(null);
        mainPageController.initialize(null, null);

        assertEquals(2, mainPageController.moreActionsContextMenu.getItems().size());
        assertEquals(MAIN_PAGE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(0).getText());
        assertEquals(LOG_IN_TEXT, mainPageController.moreActionsContextMenu.getItems().get(1).getText());

        assertFalse(mainPageController.messageImage.isVisible());
    }

    @Test
    public void testInitializeRegularUser() {
        LoggedUserData.getInstance().setUserModel(new UserModel(2, "email@yahoo.com", "mypass", "My Name", UserType.RegularUser));
        mainPageController.initialize(null, null);

        assertEquals(3, mainPageController.moreActionsContextMenu.getItems().size());
        assertEquals(VIEW_PROFILE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(0).getText());
        assertEquals(MAIN_PAGE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(1).getText());
        assertEquals(LOG_OUT_TEXT, mainPageController.moreActionsContextMenu.getItems().get(2).getText());

        assertFalse(mainPageController.messageImage.isVisible());
    }

    @Test
    public void testInitializeBarManager() {
        when(discussionService.checkNewMessage(6)).thenReturn(false);
        LoggedUserData.getInstance().setUserModel(new UserModel(6, "bar.email@yahoo.com", "parola", "My Bar Name", UserType.Manager));
        mainPageController.initialize(null, null);

        assertEquals(4, mainPageController.moreActionsContextMenu.getItems().size());
        assertEquals(VIEW_PROFILE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(0).getText());
        assertEquals(CREATE_EVENT_TEXT, mainPageController.moreActionsContextMenu.getItems().get(1).getText());
        assertEquals(MAIN_PAGE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(2).getText());
        assertEquals(LOG_OUT_TEXT, mainPageController.moreActionsContextMenu.getItems().get(3).getText());

        assertTrue(mainPageController.messageImage.isVisible());
        assertEquals(mainPageController.noMessages, mainPageController.messageImage.getImage());
    }


    @Test
    public void testInitializeArtist() {
        when(discussionService.checkNewMessage(13)).thenReturn(true);
        LoggedUserData.getInstance().setUserModel(new UserModel(13, "email.artist@yahoo.com", "artistpassword", "Name", UserType.Artist));
        mainPageController.initialize(null, null);

        assertEquals(3, mainPageController.moreActionsContextMenu.getItems().size());
        assertEquals(VIEW_PROFILE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(0).getText());
        assertEquals(MAIN_PAGE_TEXT, mainPageController.moreActionsContextMenu.getItems().get(1).getText());
        assertEquals(LOG_OUT_TEXT, mainPageController.moreActionsContextMenu.getItems().get(2).getText());

        assertTrue(mainPageController.messageImage.isVisible());
        assertEquals(mainPageController.newMessages, mainPageController.messageImage.getImage());
    }


    @Test
    public void testOnMessageClick() {
        Image messageImage = mainPageController.messageImage.getImage();

        LoggedUserData.getInstance().setUserModel(null);
        mainPageController.onMessageClick(null);

        assertEquals(messageImage, mainPageController.messageImage.getImage());

        LoggedUserData.getInstance().setUserModel(new UserModel(51, "artist.mail@yahoo.com", "password", "ARITST NAME", UserType.Artist));
        mainPageController.onMessageClick(null);

        assertEquals(mainPageController.noMessages, mainPageController.messageImage.getImage());
    }
}