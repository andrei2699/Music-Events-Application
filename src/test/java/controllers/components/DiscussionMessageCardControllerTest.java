package controllers.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.cards.DiscussionMessageCardModel;
import models.other.Message;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class DiscussionMessageCardControllerTest extends ApplicationTest {

    private DiscussionMessageCardController discussionMessageCardController;
    private Message dummyMessage;

    private static final String DATE = "02-11-2020";
    private static final String TEXT = "Hello!";
    private static final int SENDER_ID = 2;

    @Before
    public void setUp() {
        discussionMessageCardController = new DiscussionMessageCardController();
        discussionMessageCardController.messageCardHBox = new HBox();
        discussionMessageCardController.messageVBox = new VBox();
        discussionMessageCardController.messageLabel = new Label();
        discussionMessageCardController.dateLabel = new Label();

        dummyMessage = new Message(DATE, TEXT, SENDER_ID);
    }

    @Test
    public void testUpdateItem() {
        discussionMessageCardController.updateItem(new DiscussionMessageCardModel(null, true));
        assertTrue(discussionMessageCardController.messageLabel.getText().isEmpty());
        assertTrue(discussionMessageCardController.dateLabel.getText().isEmpty());

        discussionMessageCardController.updateItem(new DiscussionMessageCardModel(dummyMessage, true));
        assertEquals(TEXT, discussionMessageCardController.messageLabel.getText());
        assertEquals(DATE, discussionMessageCardController.dateLabel.getText());
        assertTrue(discussionMessageCardController.messageVBox.getStyleClass().contains("message-sender"));
        assertFalse(discussionMessageCardController.messageVBox.getStyleClass().contains("message-receiver"));
        assertEquals(Pos.CENTER_RIGHT, discussionMessageCardController.messageCardHBox.getAlignment());

        discussionMessageCardController.updateItem(new DiscussionMessageCardModel(dummyMessage, false));
        assertEquals(TEXT, discussionMessageCardController.messageLabel.getText());
        assertEquals(DATE, discussionMessageCardController.dateLabel.getText());
        assertFalse(discussionMessageCardController.messageVBox.getStyleClass().contains("message-sender"));
        assertTrue(discussionMessageCardController.messageVBox.getStyleClass().contains("message-receiver"));
        assertEquals(Pos.CENTER_LEFT, discussionMessageCardController.messageCardHBox.getAlignment());

    }
}