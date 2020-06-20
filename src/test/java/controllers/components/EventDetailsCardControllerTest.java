package controllers.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.LoggedUserData;
import main.SceneSwitchController;
import models.BarModel;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.cards.BarCardModel;
import models.cards.EventCardModel;
import models.other.UserType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import services.IReservationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventDetailsCardControllerTest extends ApplicationTest {
    @Mock
    private EventCardModel dummyEventCardModel;

    @Mock
    private IReservationService reservationService;

    private EventDetailsCardController eventDetailsCardController;

    private EventModel dummyEventModel;

    private static final String BAR_NAME = "My Bar Name";
    private static final String ARTIST_NAME = "My Artist Name";
    private static final String EVENT_NAME = "My Event Name";
    private static final String DATE = "04-10-2020";
    private static final int TOTAL_SEATS = 100;
    private static final int START_HOUR = 18;

    @Before
    public void setUp() {
        eventDetailsCardController = new EventDetailsCardController(reservationService);

        eventDetailsCardController.eventCardVBox = new VBox();
        eventDetailsCardController.eventNameLabel = new Label();
        eventDetailsCardController.barNameLabel = new Label();
        eventDetailsCardController.artistNameLabel = new Label();
        eventDetailsCardController.dateLabel = new Label();
        eventDetailsCardController.startHourLabel = new Label();
        eventDetailsCardController.numberOfSeatsLabel = new Label();
        eventDetailsCardController.descriptionLabel = new Label();
        eventDetailsCardController.reserveTicketButton = new Button();
        eventDetailsCardController.editEventButton = new Button();
        eventDetailsCardController.notEditableMessageLabel = new Label();
        eventDetailsCardController.actionButtonsHBox = new HBox();
        eventDetailsCardController.numberOfSeatsHBox = new HBox();
        eventDetailsCardController.detailsTitledPaneContentVBox = new VBox();
        eventDetailsCardController.actionButtonsSeparator = new Separator();

        dummyEventModel = new EventModel(7, 1, 6, EVENT_NAME, DATE, START_HOUR, TOTAL_SEATS);
        dummyEventModel.setReserved_seats(10);
    }

    @Test
    public void testUpdateItemEmpty() {
        eventDetailsCardController.updateItem(null, true);
        assertNull(eventDetailsCardController.getGraphic());

        eventDetailsCardController.updateItem(dummyEventCardModel, true);
        assertNull(eventDetailsCardController.getGraphic());

        eventDetailsCardController.updateItem(null, false);
        assertNull(eventDetailsCardController.getGraphic());

        eventDetailsCardController.updateItem(new BarCardModel(new BarModel(2, "Address")), false);
        assertNull("Invalid TableCardModel", eventDetailsCardController.getGraphic());
    }

    @Test
    public void updateItem() {
        when(dummyEventCardModel.getEventModel()).thenReturn(dummyEventModel);
        when(dummyEventCardModel.getArtistName()).thenReturn(ARTIST_NAME);
        when(dummyEventCardModel.getBarName()).thenReturn(BAR_NAME);

        eventDetailsCardController.updateItem(dummyEventCardModel, false);

        assertEquals(dummyEventModel.getReserved_seats() + " / " + dummyEventModel.getTotal_seats(), eventDetailsCardController.numberOfSeatsLabel.getText());
        assertEquals(EVENT_NAME, eventDetailsCardController.eventNameLabel.getText());
        assertEquals(BAR_NAME, eventDetailsCardController.barNameLabel.getText());
        assertEquals(ARTIST_NAME, eventDetailsCardController.artistNameLabel.getText());
        assertEquals(DATE, eventDetailsCardController.dateLabel.getText());
        assertEquals(String.valueOf(START_HOUR), eventDetailsCardController.startHourLabel.getText());
        assertEquals(0, eventDetailsCardController.actionButtonsHBox.getChildren().size());
        assertNotNull(eventDetailsCardController.getGraphic());
    }

    @Test
    public void updateItemRegularUser() {
        LoggedUserData.getInstance().setUserModel(new UserModel(1, "email@yahoo.com", "pass", "Name", UserType.RegularUser));
        updateItem();
        assertFalse(eventDetailsCardController.actionButtonsHBox.getChildren().contains(eventDetailsCardController.editEventButton));
    }

    @Test
    public void updateItemBarManager() {
        UserModel barManagerModel = new UserModel(2, "email@yahoo.com", "pass", "Name", UserType.Manager);

        LoggedUserData.getInstance().setUserModel(barManagerModel);
        updateItem();

        barManagerModel = new UserModel(1, "email@yahoo.com", "pass", "Name", UserType.Manager);

        LoggedUserData.getInstance().setUserModel(barManagerModel);
        List<ReservationModel> list = new ArrayList<>();
        list.add(new ReservationModel(1, 2, 3, 100));
        when(reservationService.getReservationUsingEventId(dummyEventModel.getId())).thenReturn(list);
        updateItem();
        assertTrue(eventDetailsCardController.editEventButton.isDisabled());
        assertTrue(eventDetailsCardController.notEditableMessageLabel.isVisible());
        assertFalse(eventDetailsCardController.actionButtonsHBox.getChildren().contains(eventDetailsCardController.reserveTicketButton));
    }

    @Test
    public void updateItemNotAvailableSeats() {
        EventModel eventModel = new EventModel(7, 1, 6, EVENT_NAME, DATE, START_HOUR, TOTAL_SEATS);
        eventModel.setReserved_seats(TOTAL_SEATS);

        when(dummyEventCardModel.getEventModel()).thenReturn(eventModel);
        when(dummyEventCardModel.getArtistName()).thenReturn(ARTIST_NAME);
        when(dummyEventCardModel.getBarName()).thenReturn(BAR_NAME);

        eventDetailsCardController.updateItem(dummyEventCardModel, false);

        assertEquals(eventModel.getReserved_seats() + " / " + eventModel.getTotal_seats(), eventDetailsCardController.numberOfSeatsLabel.getText());
        assertEquals(EVENT_NAME, eventDetailsCardController.eventNameLabel.getText());
        assertEquals(BAR_NAME, eventDetailsCardController.barNameLabel.getText());
        assertEquals(ARTIST_NAME, eventDetailsCardController.artistNameLabel.getText());
        assertEquals(DATE, eventDetailsCardController.dateLabel.getText());
        assertEquals(String.valueOf(START_HOUR), eventDetailsCardController.startHourLabel.getText());
        assertEquals(0, eventDetailsCardController.actionButtonsHBox.getChildren().size());
        assertTrue(eventDetailsCardController.reserveTicketButton.isDisabled());
        assertNotNull(eventDetailsCardController.getGraphic());
    }
}