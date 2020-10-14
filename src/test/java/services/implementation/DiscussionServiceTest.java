package services.implementation;

import models.DiscussionModel;
import models.other.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IDiscussionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscussionServiceTest {
    @Mock
    private IRepository<DiscussionModel> repository;

    private IDiscussionService discussionService;

    private List<DiscussionModel> dummyDiscussionModels;

    @Before
    public void setUp() {
        discussionService = new DiscussionServiceImpl(repository);

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

    @After
    public void tearDown() {
        discussionService = null;
    }

    @Test
    public void testGetDiscussion() {
        when(repository.getAll()).thenReturn(dummyDiscussionModels);

        assertEquals(dummyDiscussionModels.get(0), discussionService.getDiscussion(0));
        assertEquals(dummyDiscussionModels.get(3), discussionService.getDiscussion(3));
        assertEquals(dummyDiscussionModels.get(5), discussionService.getDiscussion(5));

        assertNull(discussionService.getDiscussion(51));

        when(repository.getAll()).thenReturn(new ArrayList<>());
        assertNull(discussionService.getDiscussion(4));

        when(repository.getAll()).thenReturn(null);
        assertNull(discussionService.getDiscussion(2));
    }

    @Test
    public void testGetAllDiscussions() {
        when(repository.getAll()).thenReturn(dummyDiscussionModels);
        assertEquals(dummyDiscussionModels, discussionService.getAllDiscussions());
    }

    @Test
    public void testCreateDiscussion() {
        when(repository.getAll()).thenReturn(null);
        try {
            discussionService.createDiscussion(5, 6);
        } catch (NullPointerException | DiscussionNotCreatedException e) {
            fail();
        }
    }

    @Test
    public void testGetDiscussionsUsingId() {
        when(repository.getAll()).thenReturn(dummyDiscussionModels);

        List<DiscussionModel> discussionsUsingId = discussionService.getDiscussionsUsingId(11);
        assertEquals("Not same size", 1, discussionsUsingId.size());
        assertEquals(dummyDiscussionModels.get(5), discussionsUsingId.get(0));

        discussionsUsingId = discussionService.getDiscussionsUsingId(12);
        assertEquals("Not same size", 1, discussionsUsingId.size());
        assertEquals(dummyDiscussionModels.get(5), discussionsUsingId.get(0));

        discussionsUsingId = discussionService.getDiscussionsUsingId(2);
        assertEquals("Not same size", 3, discussionsUsingId.size());
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(0)));
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(1)));
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(2)));

        discussionsUsingId = discussionService.getDiscussionsUsingId(4);
        assertEquals("Not same size", 3, discussionsUsingId.size());
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(0)));
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(1)));
        assertTrue(discussionsUsingId.contains(dummyDiscussionModels.get(3)));

        discussionsUsingId = discussionService.getDiscussionsUsingId(66);
        assertEquals(0, discussionsUsingId.size());

        when(repository.getAll()).thenReturn(dummyDiscussionModels);
        discussionsUsingId = discussionService.getDiscussionsUsingId(51);
        assertEquals(0, discussionsUsingId.size());

        when(repository.getAll()).thenReturn(null);
        discussionsUsingId = discussionService.getDiscussionsUsingId(71);
        assertEquals(0, discussionsUsingId.size());
    }

    @Test
    public void testCheckNewMessage() {
        when(repository.getAll()).thenReturn(dummyDiscussionModels);

        assertTrue(discussionService.checkNewMessage(2));
        assertTrue(discussionService.checkNewMessage(3));
        assertFalse(discussionService.checkNewMessage(4));
        assertFalse("New Conversation, no messages", discussionService.checkNewMessage(6));
        assertTrue(discussionService.checkNewMessage(11));
        assertFalse(discussionService.checkNewMessage(12));

        assertFalse("Not in list", discussionService.checkNewMessage(45));

        when(repository.getAll()).thenReturn(null);
        assertFalse(discussionService.checkNewMessage(0));
    }
}
