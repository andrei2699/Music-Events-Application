package models.cards;

import export.ExportRow;
import main.LoggedUserData;
import models.BarModel;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.IBarService;
import services.IEventService;
import services.IUserService;

import java.util.List;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationCardModelTest {

    @Mock
    IUserService userService;
    @Mock
    IEventService eventService;
    @Mock
    IBarService barService;

    private UserModel userModelBar;
    private UserModel userModelArtist;
    private EventModel eventModel;
    private BarModel barModel;

    @Before
    public void setUp() {
        userModelBar = new UserModel(13, "bar@yahoo.com", "password", "Bar Name", UserType.Manager);
        eventModel = new EventModel(29, 13, 81, "Super Event Name", 10,"2020-08-09", 14, 120, "Interesting description");
        barModel = new BarModel(userModelBar.getId(), "Straad Mihai Viteazu Nr 17");
        userModelArtist = new UserModel(81, "artist@yahoo.com", "password", "Band Name", UserType.Artist);
    }

    @Test
    public void testContainsFilterForEventName() {
        when(userService.getUser(13)).thenReturn(userModelBar);
        when(userService.getUser(81)).thenReturn(userModelArtist);
        when(eventService.getEventUsingEventId(29)).thenReturn(eventModel);
        when(barService.getBar(eventModel.getBar_manager_id())).thenReturn(barModel);

        ReservationModel reservationModel = new ReservationModel(14, 67, 29, 15);
        ReservationCardModel reservationCardModel = new ReservationCardModel(reservationModel, userService, eventService, barService);

        assertTrue(reservationCardModel.containsFilter("Super Event Name"));
        assertTrue(reservationCardModel.containsFilter("SUPER event NAme"));
        assertTrue(reservationCardModel.containsFilter("SupER"));
        assertTrue(reservationCardModel.containsFilter("EvenT NamE"));
        assertTrue(reservationCardModel.containsFilter("super event"));
        assertTrue(reservationCardModel.containsFilter("R event N"));
        assertTrue(reservationCardModel.containsFilter("event"));

        assertFalse(reservationCardModel.containsFilter("superEventName"));
        assertFalse(reservationCardModel.containsFilter("EVENTNAME"));
        assertFalse(reservationCardModel.containsFilter("Super Name"));
        assertFalse(reservationCardModel.containsFilter("super  "));
    }

    @Test
    public void testContainsFilterForBarName() {
        when(userService.getUser(13)).thenReturn(userModelBar);
        when(userService.getUser(81)).thenReturn(userModelArtist);
        when(eventService.getEventUsingEventId(29)).thenReturn(eventModel);
        when(barService.getBar(eventModel.getBar_manager_id())).thenReturn(barModel);

        ReservationModel reservationModel = new ReservationModel(14, 67, 29, 15);
        ReservationCardModel reservationCardModel = new ReservationCardModel(reservationModel, userService, eventService, barService);

        assertTrue(reservationCardModel.containsFilter("Bar Name"));
        assertTrue(reservationCardModel.containsFilter("BAR"));
        assertTrue(reservationCardModel.containsFilter("Name"));
        assertTrue(reservationCardModel.containsFilter("bar name"));
        assertTrue(reservationCardModel.containsFilter("bar N"));

        assertFalse(reservationCardModel.containsFilter("BARNAME"));
        assertFalse(reservationCardModel.containsFilter("BarName"));
        assertFalse(reservationCardModel.containsFilter("Bra Name"));
        assertFalse(reservationCardModel.containsFilter("Mane"));
    }

    @Test
    public void testContainsFilterForBandName() {
        when(userService.getUser(13)).thenReturn(userModelBar);
        when(userService.getUser(81)).thenReturn(userModelArtist);
        when(eventService.getEventUsingEventId(29)).thenReturn(eventModel);
        when(barService.getBar(eventModel.getBar_manager_id())).thenReturn(barModel);

        ReservationModel reservationModel = new ReservationModel(14, 67, 29, 15);
        ReservationCardModel reservationCardModel = new ReservationCardModel(reservationModel, userService, eventService, barService);

        assertTrue(reservationCardModel.containsFilter("Band Name"));
        assertTrue(reservationCardModel.containsFilter("band"));
        assertTrue(reservationCardModel.containsFilter("BAND NAME"));
        assertTrue(reservationCardModel.containsFilter("Name"));
        assertTrue(reservationCardModel.containsFilter("AND"));
        assertTrue(reservationCardModel.containsFilter("ame"));
        assertTrue(reservationCardModel.containsFilter("band n"));

        assertFalse(reservationCardModel.containsFilter("bandname"));
        assertFalse(reservationCardModel.containsFilter("BandName"));
        assertFalse(reservationCardModel.containsFilter("BNDNAME"));
        assertFalse(reservationCardModel.containsFilter("nme"));
    }

    @Test
    public void testContainsFilterForCombinationOfNames() {
        when(userService.getUser(13)).thenReturn(userModelBar);
        when(userService.getUser(81)).thenReturn(userModelArtist);
        when(eventService.getEventUsingEventId(29)).thenReturn(eventModel);
        when(barService.getBar(eventModel.getBar_manager_id())).thenReturn(barModel);

        ReservationModel reservationModel = new ReservationModel(14, 67, 29, 15);
        ReservationCardModel reservationCardModel = new ReservationCardModel(reservationModel, userService, eventService, barService);

        assertFalse(reservationCardModel.containsFilter("Super Bar Band"));
        assertFalse(reservationCardModel.containsFilter("Name NAME"));
        assertFalse(reservationCardModel.containsFilter("BAND Bar"));
        assertFalse(reservationCardModel.containsFilter("super event name bar band"));
        assertFalse(reservationCardModel.containsFilter("Bar Name Band Name"));

        assertTrue(reservationCardModel.containsFilter(""));
        assertTrue(reservationCardModel.containsFilter(" "));
        assertTrue(reservationCardModel.containsFilter(null));
    }

    @Test
    public void testConvertCardModelToExportRowList() {
        when(userService.getUser(13)).thenReturn(userModelBar);
        when(userService.getUser(81)).thenReturn(userModelArtist);
        when(eventService.getEventUsingEventId(29)).thenReturn(eventModel);
        when(barService.getBar(eventModel.getBar_manager_id())).thenReturn(barModel);

        ReservationModel reservationModel = new ReservationModel(14, 67, 29, 15);
        ReservationCardModel reservationCardModel = new ReservationCardModel(reservationModel, userService, eventService, barService);

        LoggedUserData.getInstance().setUserModel(null);
        List<ExportRow> resultList = reservationCardModel.convertCardModelToExportRowList();

        assertNull(resultList);

        LoggedUserData.getInstance().setUserModel(new UserModel(98, "email@gmail.com", "pass", "Name", UserType.RegularUser));
        resultList = reservationCardModel.convertCardModelToExportRowList();

        assertEquals(8, resultList.size());

        assertEquals(EVENT_NAME_TEXT, resultList.get(0).getAttributeName());
        assertEquals(BAR_NAME_TEXT, resultList.get(1).getAttributeName());
        assertEquals(BAR_ADRESS_TEXT, resultList.get(2).getAttributeName());
        assertEquals(ARTIST_NAME_TEXT, resultList.get(3).getAttributeName());
        assertEquals(DATE_TEXT, resultList.get(4).getAttributeName());
        assertEquals(HOUR_TEXT, resultList.get(5).getAttributeName());
        assertEquals(SEAT_NUMBER_TEXT, resultList.get(6).getAttributeName());
        assertEquals(RESERVATION_MADE_BY_TEXT, resultList.get(7).getAttributeName());

        assertEquals("Super Event Name", resultList.get(0).getAttributeValue());
        assertEquals("Bar Name", resultList.get(1).getAttributeValue());
        assertEquals("Straad Mihai Viteazu Nr 17", resultList.get(2).getAttributeValue());
        assertEquals("Band Name", resultList.get(3).getAttributeValue());
        assertEquals("2020-08-09", resultList.get(4).getAttributeValue());
        assertEquals(14 + "", resultList.get(5).getAttributeValue());
        assertEquals(15 + "", resultList.get(6).getAttributeValue());
        assertEquals("Name", resultList.get(7).getAttributeValue());
    }
}