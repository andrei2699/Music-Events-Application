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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscussionServiceTest {
    @Mock
    private IRepository<DiscussionModel> repository;

    private IDiscussionService discussionService;

    private List<DiscussionModel> discussionModels;

    @Before
    public void setUp() {
        discussionService = new DiscussionServiceImpl(repository);

        discussionModels = new ArrayList<>();

        DiscussionModel model1 = new DiscussionModel(1, 2, 4);
        List<Message> messages1 = new ArrayList<>();
        messages1.add(new Message("05:11 - 2020-07-11", "Salut", 2));
        model1.setMessages(messages1);

        DiscussionModel model2 = new DiscussionModel(2, 2, 3);
        List<Message> messages2 = new ArrayList<>();
        messages1.add(new Message("05:11 - 2020-07-11", "Salut", 2));
        messages1.add(new Message("05:12 - 2020-07-11", "Salutare", 3));
        messages1.add(new Message("05:13 - 2020-07-11", "Ce faci ?", 2));
        messages1.add(new Message("05:15 - 2020-07-11", "Cum o duci ?", 2));
        model2.setMessages(messages2);

        DiscussionModel model3 = new DiscussionModel(3, 6, 4);

        DiscussionModel model4 = new DiscussionModel(4, 9, 8);

        discussionModels.add(model1);
        discussionModels.add(model2);
        discussionModels.add(model3);
        discussionModels.add(model4);
    }

    @After
    public void tearDown() {
        discussionService = null;
    }

    @Test
    public void testGetDiscussion() {
        when(repository.getAll()).thenReturn(discussionModels);
    }

    @Test
    public void testGetAllDiscussions() {

    }

    @Test
    public void testUpdateDiscussion() {

    }

    @Test
    public void testCreateDiscussion() {

    }

    @Test
    public void testGetDiscussionsUsingId() {

    }

    @Test
    public void testCheckNewMessage() {

    }
}
