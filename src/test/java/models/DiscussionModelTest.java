package models;

import models.other.Message;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DiscussionModelTest {

    private List<DiscussionModel> dummyDiscussionModels;

    @Before
    public void setUp() {
        dummyDiscussionModels = new ArrayList<>();

        DiscussionModel model0 = new DiscussionModel(0, 2, 4);
        List<Message> messages0 = new ArrayList<>();
        messages0.add(new Message("05:11 - 2020-07-11", "Salut", 2));
        messages0.get(0).setSeen(true);
        model0.setMessages(messages0);

        DiscussionModel model1 = new DiscussionModel(1, 2, 4);
        List<Message> messages1 = new ArrayList<>();
        messages1.add(new Message("05:11 - 2020-07-11", "Buna", 4));
        model1.setMessages(messages1);

        DiscussionModel model2 = new DiscussionModel(2, 2, 3);
        List<Message> messages2 = new ArrayList<>();
        messages2.add(new Message("05:11 - 2020-07-11", "Salut", 2));
        messages2.add(new Message("05:12 - 2020-07-11", "Salutare", 3));
        messages2.add(new Message("05:13 - 2020-07-11", "Ce faci ?", 2));
        messages2.add(new Message("05:15 - 2020-07-11", "Cum o duci ?", 2));
        model2.setMessages(messages2);

        DiscussionModel model3 = new DiscussionModel(3, 6, 4);

        DiscussionModel model4 = new DiscussionModel(4, 9, 8);
        List<Message> messages4 = new ArrayList<>();
        messages4.add(new Message("05:11 - 2020-07-11", "Salut", 9));
        messages4.add(new Message("05:15 - 2020-07-11", "Cum o duci ?", 9));
        for (Message message : messages4) {
            message.setSeen(true);
        }
        model4.setMessages(messages4);

        DiscussionModel model5 = new DiscussionModel(5, 11, 12);
        List<Message> messages5 = new ArrayList<>();
        messages5.add(new Message("08:15 - 2020-02-11", "Salut", 12));
        messages5.add(new Message("09:15 - 2020-02-11", "Cum merge ?", 12));
        messages5.get(0).setSeen(true);
        model5.setMessages(messages5);

        dummyDiscussionModels.add(model0);
        dummyDiscussionModels.add(model1);
        dummyDiscussionModels.add(model2);
        dummyDiscussionModels.add(model3);
        dummyDiscussionModels.add(model4);
        dummyDiscussionModels.add(model5);
    }


    @Test
    public void testHasUnreadMessagesWith() {
        assertFalse(dummyDiscussionModels.get(0).hasUnreadMessagesWith(2));
        assertFalse(dummyDiscussionModels.get(0).hasUnreadMessagesWith(4));

        assertTrue(dummyDiscussionModels.get(1).hasUnreadMessagesWith(2));
        assertFalse(dummyDiscussionModels.get(1).hasUnreadMessagesWith(4));

        assertFalse(dummyDiscussionModels.get(2).hasUnreadMessagesWith(2));
        assertTrue(dummyDiscussionModels.get(2).hasUnreadMessagesWith(3));

        assertFalse("New Conversation, no messages", dummyDiscussionModels.get(3).hasUnreadMessagesWith(6));
        assertFalse("New Conversation, no messages", dummyDiscussionModels.get(3).hasUnreadMessagesWith(4));

        assertFalse(dummyDiscussionModels.get(4).hasUnreadMessagesWith(8));
        assertFalse(dummyDiscussionModels.get(4).hasUnreadMessagesWith(9));

        assertTrue(dummyDiscussionModels.get(5).hasUnreadMessagesWith(11));
        assertFalse(dummyDiscussionModels.get(5).hasUnreadMessagesWith(12));
    }
}