package controllers.components;

import controllers.scenes.ISceneResponseCall;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.DiscussionModel;
import models.EventModel;
import models.UserModel;
import models.cards.DiscussionHeaderCardModel;
import models.cards.EventCardModel;
import models.other.Message;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IArtistService;
import services.IBarService;
import services.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscussionChatHeaderCardControllerTest extends ApplicationTest {

    @Mock
    private DiscussionHeaderCardModel dummyDiscussionHeaderCardModel;
    @Mock
    private IUserService userService;
    @Mock
    private IArtistService artistService;
    @Mock
    private IBarService barService;

    private DiscussionChatHeaderCardController discussionChatHeaderCardController;

    private DiscussionModel dummyDiscussionModel;
    private UserModel dummyUserModelArtist, dummyUserModelBar;

    @Before
    public void setUp() {
        discussionChatHeaderCardController = new DiscussionChatHeaderCardController(userService, artistService, barService);

        discussionChatHeaderCardController.profilePictureImage = new ImageView();
        discussionChatHeaderCardController.nameLabel = new Label();
        discussionChatHeaderCardController.chatHeaderHBox = new VBox();

        dummyDiscussionModel = new DiscussionModel(2, 3, 4);
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("06-04-2020", "Salut", 3));
        messageList.add(new Message("06-04-2020", "Buna !", 4));
        dummyDiscussionModel.setMessages(messageList);

        dummyUserModelArtist = new UserModel(4, "email@gmail.com", "parola", "ArtistName", UserType.Artist);
        dummyUserModelBar = new UserModel(3, "bar@gmail.com", "bar_parola", "Bar Name", UserType.Manager);
    }

    @Test
    public void testUpdateItemEmpty() {
        discussionChatHeaderCardController.updateItem(null, true);
        assertNull(discussionChatHeaderCardController.getGraphic());

        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, true);
        assertNull(discussionChatHeaderCardController.getGraphic());

        discussionChatHeaderCardController.updateItem(null, false);
        assertNull(discussionChatHeaderCardController.getGraphic());

        discussionChatHeaderCardController.updateItem(new EventCardModel(new EventModel(1, 2, 3, "Nume", "08-08-2020", 15, 100)), false);
        assertNull("Invalid TableCardModel", discussionChatHeaderCardController.getGraphic());
    }

    @Test
    public void testUpdateItemNotLoggedUser() {
        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);

        LoggedUserData.getInstance().setUserModel(new UserModel(76, "another@yahoo.com", "pass", "name", UserType.Manager));

        discussionChatHeaderCardController.setOnCardModelSet(null);
        discussionChatHeaderCardController.setOnClickResponseCall(null);
        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);

        assertTrue(discussionChatHeaderCardController.nameLabel.getText().isEmpty());

        assertNotNull(discussionChatHeaderCardController.getGraphic());
    }

    @Test
    public void testUpdateItem() {
        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);
        when(userService.getUser(4)).thenReturn(dummyUserModelArtist);
        when(userService.getUser(3)).thenReturn(dummyUserModelBar);

        discussionChatHeaderCardController.setOnCardModelSet(null);
        discussionChatHeaderCardController.setOnClickResponseCall(null);

        LoggedUserData.getInstance().setUserModel(dummyUserModelArtist);
        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);
        assertEquals(dummyUserModelBar.getName(), discussionChatHeaderCardController.nameLabel.getText());
        assertNotNull(discussionChatHeaderCardController.getGraphic());

        LoggedUserData.getInstance().setUserModel(dummyUserModelBar);
        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);
        assertEquals(dummyUserModelArtist.getName(), discussionChatHeaderCardController.nameLabel.getText());
        assertNotNull(discussionChatHeaderCardController.getGraphic());
    }

    @Test
    public void testOnMouseClicked() {
        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);
        LoggedUserData.getInstance().setUserModel(dummyUserModelArtist);

        AtomicReference<Boolean> hasResponded = new AtomicReference<>(false);
        ISceneResponseCall<DiscussionHeaderCardModel> response = callResult -> hasResponded.set(true);
        discussionChatHeaderCardController.setOnClickResponseCall(response);
        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);
        discussionChatHeaderCardController.onMouseClicked(null);

        assertTrue(hasResponded.get());
    }

    @Test
    public void testOnCardModelSet() {
        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);
        LoggedUserData.getInstance().setUserModel(dummyUserModelBar);

        AtomicReference<Boolean> hasResponded = new AtomicReference<>(false);
        ISceneResponseCall<DiscussionHeaderCardModel> response = callResult -> hasResponded.set(true);
        discussionChatHeaderCardController.setOnCardModelSet(response);
        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);

        assertTrue(hasResponded.get());
    }

    @Test
    public void testHasModelId() {
        assertFalse("Not null discussion header card model", discussionChatHeaderCardController.hasModelId(4));

        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);
        LoggedUserData.getInstance().setUserModel(dummyUserModelBar);
        when(dummyDiscussionHeaderCardModel.getDiscussionModel()).thenReturn(dummyDiscussionModel);

        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);
        assertTrue(discussionChatHeaderCardController.hasModelId(4));

        discussionChatHeaderCardController.updateItem(dummyDiscussionHeaderCardModel, false);
        assertFalse(discussionChatHeaderCardController.hasModelId(12));
    }
}