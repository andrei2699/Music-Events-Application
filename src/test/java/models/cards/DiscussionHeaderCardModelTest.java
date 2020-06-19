package models.cards;

import main.LoggedUserData;
import models.DiscussionModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.IUserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscussionHeaderCardModelTest {

    @Mock
    IUserService userService;

    @Test
    public void testToString() {
        DiscussionModel discussionModel = new DiscussionModel(1, 23, 35);
        DiscussionHeaderCardModel discussionHeaderCardModel = new DiscussionHeaderCardModel(discussionModel, userService);

        when(userService.getUser(23)).thenReturn(new UserModel(23, "bar@yahoo.com", "p1", "Tania", UserType.Manager));
        when(userService.getUser(35)).thenReturn(new UserModel(35, "artist@yahoo.com", "p2", "Andrei", UserType.Artist));

        LoggedUserData.getInstance().setUserModel(new UserModel(23, "bar@yahoo.com", "p1", "Tania", UserType.Manager));
        assertEquals("Andrei", discussionHeaderCardModel.toString());

        LoggedUserData.getInstance().setUserModel(new UserModel(35, "artist@yahoo.com", "p3", "Andrei", UserType.Artist));
        assertEquals("Tania", discussionHeaderCardModel.toString());
    }
}