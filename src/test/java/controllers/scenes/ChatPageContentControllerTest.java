package controllers.scenes;

import controllers.components.cardsTableView.CardsTableViewController;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import models.DiscussionModel;
import models.UserModel;
import models.cards.DiscussionHeaderCardModel;
import models.cards.TableCardModel;
import models.other.Message;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IDiscussionService;
import services.IUserService;

import java.util.ArrayList;
import java.util.List;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatPageContentControllerTest extends ApplicationTest {

    private ChatPageContentController chatPageContentController;

    @Mock
    private IDiscussionService discussionService;
    @Mock
    private IUserService userService;

    private static final String CELL_FACTORY_VALUE = "discussionHeaderCardModel";

    @Before
    public void setUp() {
        chatPageContentController = new ChatPageContentController(discussionService, userService);

        chatPageContentController.enterMessageTextField = new TextField();
        chatPageContentController.discussionHeaderTableViewController = new CardsTableViewController();
        chatPageContentController.discussionHeaderTableViewController.cardsTableView = new TableView<>();
        chatPageContentController.discussionHeaderTableViewController.cardsTableColumn = new TableColumn<>();
        chatPageContentController.messagesScrollPane = new ScrollPane();
        chatPageContentController.sendButton = new Button();
        chatPageContentController.conversationWithLabel = new Label();
        chatPageContentController.messagesVBox = new VBox();
    }

    @Test
    public void testInitialize() {
        chatPageContentController.initialize(null, null);

        assertEquals(NO_CONVERSATIONS_TEXT, ((Label) chatPageContentController.discussionHeaderTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals(CELL_FACTORY_VALUE, ((PropertyValueFactory<TableCardModel, TableCardModel>)
                chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(CONVERSATIONS_TEXT, chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getText());
    }

    @Test
    public void testOnSetModelIdNullLoggedUser() {
        LoggedUserData.getInstance().setUserModel(null);

        chatPageContentController.initialize(null, null);
        chatPageContentController.onSetModelId(5);
        assertNull(chatPageContentController.discussionHeaderTableViewController.getItem(0));
        assertTrue(chatPageContentController.enterMessageTextField.isDisabled());
        assertTrue(chatPageContentController.sendButton.isDisabled());
    }

    @Test
    public void testOnSetModelIdNoDiscussions() {
        LoggedUserData.getInstance().setUserModel(new UserModel(1, "email@yahoo.com", "pass", "name", UserType.Artist));
        when(discussionService.getDiscussionsUsingId(1)).thenReturn(new ArrayList<>());

        chatPageContentController.initialize(null, null);
        chatPageContentController.onSetModelId(1);

        assertEquals(NO_CONVERSATIONS_TEXT, ((Label) chatPageContentController.discussionHeaderTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals(CELL_FACTORY_VALUE, ((PropertyValueFactory<TableCardModel, TableCardModel>)
                chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(CONVERSATIONS_TEXT, chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getText());
        assertNull(chatPageContentController.discussionHeaderTableViewController.getItem(0));
        assertTrue(chatPageContentController.enterMessageTextField.isDisabled());
        assertTrue(chatPageContentController.sendButton.isDisabled());
    }

    @Test
    public void testOnSetModelIdWithDiscussions() {
        LoggedUserData.getInstance().setUserModel(new UserModel(2, "local.bar@yahoo.com", "password", "manager name", UserType.Manager));
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(1, 2, 5));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("07-04-2020", "Salut", 2));
        messages.add(new Message("07-04-2020", "Salutare", 5));
        discussionModels.get(0).setMessages(messages);
        discussionModels.add(new DiscussionModel(6, 2, 6));
        discussionModels.add(new DiscussionModel(9, 2, 8));

        when(discussionService.getDiscussionsUsingId(2)).thenReturn(discussionModels);
        when(userService.getUser(5)).thenReturn(new UserModel(5, "artist2@yahoo.com", "psdasdccw", "Artist2", UserType.Artist));

        chatPageContentController.initialize(null, null);
        chatPageContentController.onSetModelId(5);

        assertEquals(NO_CONVERSATIONS_TEXT, ((Label) chatPageContentController.discussionHeaderTableViewController.cardsTableView.getPlaceholder()).getText());
        assertEquals(CELL_FACTORY_VALUE, ((PropertyValueFactory<TableCardModel, TableCardModel>)
                chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getCellValueFactory()).getProperty());
        assertEquals(CONVERSATIONS_TEXT, chatPageContentController.discussionHeaderTableViewController.cardsTableColumn.getText());

        assertEquals(discussionModels.get(0), ((DiscussionHeaderCardModel) chatPageContentController.discussionHeaderTableViewController.getItem(0)).getDiscussionModel());

        assertEquals(messages.size(), chatPageContentController.messagesVBox.getChildren().size());
        assertEquals(CONVERSATION_WITH_TEXT + " " + chatPageContentController.discussionHeaderTableViewController.getItem(0).toString(), chatPageContentController.conversationWithLabel.getText());
    }

    @Test
    public void testOnSetModelId() {
        LoggedUserData.getInstance().setUserModel(new UserModel(6, "artist@yahoo.com", "parola", "artist name", UserType.Artist));
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(6, 3, 6));
        discussionModels.add(new DiscussionModel(1, 1, 6));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("01-05-2020", "Buna !", 6));
        messages.add(new Message("01-05-2020", "Buna", 1));
        discussionModels.get(1).setMessages(messages);
        discussionModels.add(new DiscussionModel(9, 7, 6));

        when(discussionService.getDiscussionsUsingId(6)).thenReturn(discussionModels);
        when(userService.getUser(1)).thenReturn(new UserModel(1, "bar1@yahoo.com", "psfwdkkdew", "Bar", UserType.Manager));

        chatPageContentController.initialize(null, null);

        chatPageContentController.onSetModelId(1);

        assertEquals(2, chatPageContentController.messagesVBox.getChildren().size());
        assertEquals(CONVERSATION_WITH_TEXT + " " + chatPageContentController.discussionHeaderTableViewController.getItem(1).toString(),
                chatPageContentController.conversationWithLabel.getText());
    }

    @Test
    public void testOnSendButtonClickValidMessage() {
        chatPageContentController.enterMessageTextField.setText("Text");

        LoggedUserData.getInstance().setUserModel(new UserModel(7, "mail@yahoo.com", "kowpassword", "Mein Name", UserType.Manager));
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(1, 2, 5));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("07-04-2020", "Salut", 2));
        messages.add(new Message("07-04-2020", "Salutare", 5));
        discussionModels.get(0).setMessages(messages);
        discussionModels.add(new DiscussionModel(6, 2, 6));
        discussionModels.add(new DiscussionModel(9, 2, 8));

        chatPageContentController.initialize(null, null);
        int size = chatPageContentController.messagesVBox.getChildren().size();
        chatPageContentController.onSendButtonClick(null);
        assertEquals(size, chatPageContentController.messagesVBox.getChildren().size());
        assertEquals("Text", chatPageContentController.enterMessageTextField.getText());
    }

    @Test
    public void testOnSendButtonClickEmptyMessage() {
        chatPageContentController.enterMessageTextField.setText("");

        LoggedUserData.getInstance().setUserModel(new UserModel(7, "mail@yahoo.com", "kowpassword", "Mein Name", UserType.Manager));
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(1, 2, 5));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("07-04-2020", "Salut", 2));
        messages.add(new Message("07-04-2020", "Salutare", 5));
        discussionModels.get(0).setMessages(messages);
        discussionModels.add(new DiscussionModel(6, 2, 6));
        discussionModels.add(new DiscussionModel(9, 2, 8));

        chatPageContentController.initialize(null, null);
        int size = chatPageContentController.messagesVBox.getChildren().size();
        chatPageContentController.onSendButtonClick(null);
        assertEquals(size, chatPageContentController.messagesVBox.getChildren().size());
        assertTrue(chatPageContentController.enterMessageTextField.getText().isEmpty());
    }

    @Test
    public void testOnSendButtonClickNoLoggedUser() {
        chatPageContentController.enterMessageTextField.setText("Message");

        LoggedUserData.getInstance().setUserModel(null);
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(1, 2, 5));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("07-04-2020", "Salut", 2));
        messages.add(new Message("07-04-2020", "Salutare", 5));
        discussionModels.get(0).setMessages(messages);

        discussionModels.add(new DiscussionModel(6, 2, 6));
        discussionModels.add(new DiscussionModel(9, 2, 8));

        chatPageContentController.initialize(null, null);
        int size = chatPageContentController.messagesVBox.getChildren().size();
        chatPageContentController.onSendButtonClick(null);
        assertEquals(size, chatPageContentController.messagesVBox.getChildren().size());
        assertEquals("Message", chatPageContentController.enterMessageTextField.getText());
    }

    @Test
    public void testOnSendButtonClickNoOpenedHeaderCardModel() {
        chatPageContentController.enterMessageTextField.setText("New Message");

        int size = chatPageContentController.messagesVBox.getChildren().size();
        chatPageContentController.onSendButtonClick(null);
        assertEquals(size, chatPageContentController.messagesVBox.getChildren().size());
        assertEquals("New Message", chatPageContentController.enterMessageTextField.getText());
    }

    @Test
    public void testSwitchConversationNullLoggedUser() {
        LoggedUserData.getInstance().setUserModel(new UserModel(6, "artist@yahoo.com", "parola", "artist name", UserType.Artist));
        ArrayList<DiscussionModel> discussionModels = new ArrayList<>();
        discussionModels.add(new DiscussionModel(6, 3, 6));
        discussionModels.add(new DiscussionModel(1, 1, 6));
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("03-05-2020", "Buna !", 6));
        messages.add(new Message("03-05-2020", "Buna", 1));
        discussionModels.get(1).setMessages(messages);
        discussionModels.add(new DiscussionModel(9, 7, 6));

        chatPageContentController.initialize(null, null);
        LoggedUserData.getInstance().setUserModel(null);
        chatPageContentController.onSetModelId(1);

        assertEquals(0, chatPageContentController.messagesVBox.getChildren().size());
    }
}