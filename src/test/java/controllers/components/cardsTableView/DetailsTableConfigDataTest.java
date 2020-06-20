package controllers.components.cardsTableView;

import org.junit.Test;

import static main.ApplicationResourceStrings.*;
import static org.junit.Assert.assertEquals;

public class DetailsTableConfigDataTest {

    @Test
    public void testGetEventTableColumnData() {
        DetailsTableConfigData eventTableColumnData = DetailsTableConfigData.getEventTableColumnData();

        assertEquals(AVAILABLE_EVENTS_TEXT, eventTableColumnData.getTableColumnText());
        assertEquals("eventCardModel", eventTableColumnData.getPropertyValueFactory());
        assertEquals(NO_EVENTS_TEXT, eventTableColumnData.getNoContentLabelText());
    }

    @Test
    public void testGetBarTableColumnData() {
        DetailsTableConfigData eventTableColumnData = DetailsTableConfigData.getBarTableColumnData();

        assertEquals(BARS_TEXT, eventTableColumnData.getTableColumnText());
        assertEquals("barCardModel", eventTableColumnData.getPropertyValueFactory());
        assertEquals(NO_BARS_TEXT, eventTableColumnData.getNoContentLabelText());
    }

    @Test
    public void testGetArtistTableColumnData() {
        DetailsTableConfigData eventTableColumnData = DetailsTableConfigData.getArtistTableColumnData();

        assertEquals(ARTISTS_TEXT, eventTableColumnData.getTableColumnText());
        assertEquals("artistCardModel", eventTableColumnData.getPropertyValueFactory());
        assertEquals(NO_ARTISTS_TEXT, eventTableColumnData.getNoContentLabelText());
    }

    @Test
    public void testGetReservationTableColumnData() {
        DetailsTableConfigData eventTableColumnData = DetailsTableConfigData.getReservationTableColumnData();

        assertEquals(MY_RESERVATIONS_TEXT, eventTableColumnData.getTableColumnText());
        assertEquals("reservationCardModel", eventTableColumnData.getPropertyValueFactory());
        assertEquals(NO_RESERVATIONS_TEXT, eventTableColumnData.getNoContentLabelText());
    }
}