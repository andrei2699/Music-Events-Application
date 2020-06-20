package controllers.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.ArtistModel;
import models.EventModel;
import models.ReservationModel;
import models.cards.ArtistCardModel;
import models.cards.ReservationCardModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationDetailsCardControllerTest extends ApplicationTest {

    @Mock
    private ReservationCardModel dummyReservationCardModel;

    private ReservationDetailsCardController reservationDetailsCardController;

    private ReservationModel dummyReservationModel;
    private EventModel dummyEventModel;

    private static final String BAR_NAME = "My Bar Name";
    private static final String ARTIST_NAME = "My Artist Name";
    private static final String EVENT_NAME = "My Event Name";
    private static final String DATE = "04-10-2020";
    private static final int RESERVED_SEATS = 10;
    private static final int TOTAL_SEATS = 100;
    private static final int START_HOUR = 18;

    @Before
    public void setUp() {
        reservationDetailsCardController = new ReservationDetailsCardController();
        reservationDetailsCardController.artistNameLabel = new Label();
        reservationDetailsCardController.barNameLabel = new Label();
        reservationDetailsCardController.dateLabel = new Label();
        reservationDetailsCardController.eventNameLabel = new Label();
        reservationDetailsCardController.numberOfSeatsLabel = new Label();
        reservationDetailsCardController.startHourLabel = new Label();
        reservationDetailsCardController.reservationDetailsCardVBox = new VBox();
        reservationDetailsCardController.exportPDFButton = new Button();

        dummyReservationModel = new ReservationModel(1, 2, 3, RESERVED_SEATS);
        dummyEventModel = new EventModel(4, 5, 6, EVENT_NAME, DATE, START_HOUR, TOTAL_SEATS);
    }

    @Test
    public void testUpdateItemEmpty() {
        reservationDetailsCardController.updateItem(null, true);
        assertNull(reservationDetailsCardController.getGraphic());

        reservationDetailsCardController.updateItem(dummyReservationCardModel, true);
        assertNull(reservationDetailsCardController.getGraphic());

        reservationDetailsCardController.updateItem(null, false);
        assertNull(reservationDetailsCardController.getGraphic());

        reservationDetailsCardController.updateItem(new ArtistCardModel(new ArtistModel(1, false, "Rock")), false);
        assertNull("Invalid TableCardModel", reservationDetailsCardController.getGraphic());
    }

    @Test
    public void testUpdateItem() {
        when(dummyReservationCardModel.getReservationModel()).thenReturn(dummyReservationModel);
        when(dummyReservationCardModel.getEventModel()).thenReturn(dummyEventModel);
        when(dummyReservationCardModel.getArtistName()).thenReturn(ARTIST_NAME);
        when(dummyReservationCardModel.getBarName()).thenReturn(BAR_NAME);

        reservationDetailsCardController.updateItem(dummyReservationCardModel, false);

        assertEquals(String.valueOf((dummyReservationModel.getReserved_seats())), reservationDetailsCardController.numberOfSeatsLabel.getText());
        assertEquals(EVENT_NAME, reservationDetailsCardController.eventNameLabel.getText());
        assertEquals(BAR_NAME, reservationDetailsCardController.barNameLabel.getText());
        assertEquals(ARTIST_NAME, reservationDetailsCardController.artistNameLabel.getText());
        assertEquals(DATE, reservationDetailsCardController.dateLabel.getText());
        assertEquals(String.valueOf(START_HOUR), reservationDetailsCardController.startHourLabel.getText());

        assertNotNull(reservationDetailsCardController.getGraphic());
    }
}