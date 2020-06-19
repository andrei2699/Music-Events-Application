package models.cards;

import models.EventModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.IUserService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventCardModelTest {

    @Mock
    IUserService userService;

    @Test
    public void testContainsFilterForEventName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "bar@yahoo.com", "password", "Bar Name", UserType.Manager));
        when(userService.getUser(81)).thenReturn(new UserModel(81, "artist@yahoo.com", "password", "Artist Name", UserType.Artist));

        EventModel eventModel = new EventModel(29, 13, 81, "Super Event Name", "2020-08-09", 14, 120, "Interesting description");
        EventCardModel eventCardModel = new EventCardModel(eventModel, userService);

        assertTrue(eventCardModel.containsFilter("Super Event Name"));
        assertTrue(eventCardModel.containsFilter("SUPER event NAme"));
        assertTrue(eventCardModel.containsFilter("SupER"));
        assertTrue(eventCardModel.containsFilter("EvenT NamE"));
        assertTrue(eventCardModel.containsFilter("super event"));
        assertTrue(eventCardModel.containsFilter("R event N"));
        assertTrue(eventCardModel.containsFilter("event"));

        assertFalse(eventCardModel.containsFilter("superEventName"));
        assertFalse(eventCardModel.containsFilter("EVENTNAME"));
        assertFalse(eventCardModel.containsFilter("Super Name"));
        assertFalse(eventCardModel.containsFilter("super  "));
    }

    @Test
    public void testContainsFilterForArtistName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "bar@yahoo.com", "password", "Bar Name", UserType.Manager));
        when(userService.getUser(81)).thenReturn(new UserModel(81, "artist@yahoo.com", "password", "Band Name", UserType.Artist));

        EventModel eventModel = new EventModel(29, 13, 81, "Super Event Name", "2020-08-09", 14, 120, "Interesting description");
        EventCardModel eventCardModel = new EventCardModel(eventModel, userService);

        assertTrue(eventCardModel.containsFilter("Band Name"));
        assertTrue(eventCardModel.containsFilter("band"));
        assertTrue(eventCardModel.containsFilter("BAND NAME"));
        assertTrue(eventCardModel.containsFilter("Name"));
        assertTrue(eventCardModel.containsFilter("AND"));
        assertTrue(eventCardModel.containsFilter("ame"));
        assertTrue(eventCardModel.containsFilter("band n"));

        assertFalse(eventCardModel.containsFilter("bandname"));
        assertFalse(eventCardModel.containsFilter("BandName"));
        assertFalse(eventCardModel.containsFilter("BNDNAME"));
        assertFalse(eventCardModel.containsFilter("nme"));
    }

    @Test
    public void testContainsFilterForBarName() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "bar@yahoo.com", "password", "Bar Name", UserType.Manager));
        when(userService.getUser(81)).thenReturn(new UserModel(81, "artist@yahoo.com", "password", "Band Name", UserType.Artist));

        EventModel eventModel = new EventModel(29, 13, 81, "Super Event Name", "2020-08-09", 14, 120, "Interesting description");
        EventCardModel eventCardModel = new EventCardModel(eventModel, userService);

        assertTrue(eventCardModel.containsFilter("Bar Name"));
        assertTrue(eventCardModel.containsFilter("BAR"));
        assertTrue(eventCardModel.containsFilter("Name"));
        assertTrue(eventCardModel.containsFilter("bar name"));
        assertTrue(eventCardModel.containsFilter("bar N"));

        assertFalse(eventCardModel.containsFilter("BARNAME"));
        assertFalse(eventCardModel.containsFilter("BarName"));
        assertFalse(eventCardModel.containsFilter("Bra Name"));
        assertFalse(eventCardModel.containsFilter("Mane"));
    }

    @Test
    public void testContainsFilterCombinationsOfNames() {
        when(userService.getUser(13)).thenReturn(new UserModel(13, "bar@yahoo.com", "password", "Bar Name", UserType.Manager));
        when(userService.getUser(81)).thenReturn(new UserModel(81, "artist@yahoo.com", "password", "Band Name", UserType.Artist));

        EventModel eventModel = new EventModel(29, 13, 81, "Super Event Name", "2020-08-09", 14, 120, "Interesting description");
        EventCardModel eventCardModel = new EventCardModel(eventModel, userService);

        assertFalse(eventCardModel.containsFilter("Super Bar Band"));
        assertFalse(eventCardModel.containsFilter("Name NAME"));
        assertFalse(eventCardModel.containsFilter("BAND Bar"));
        assertFalse(eventCardModel.containsFilter("super event name bar band"));
        assertFalse(eventCardModel.containsFilter("Bar Name Band Name"));

        assertTrue(eventCardModel.containsFilter(""));
        assertTrue(eventCardModel.containsFilter(" "));
        assertTrue(eventCardModel.containsFilter(null));
    }
}