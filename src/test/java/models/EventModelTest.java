package models;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class EventModelTest extends ApplicationTest {

    EventModel eventModel;

    @Before
    public void setUp() {
        eventModel = new EventModel(43, 54, 34, "Name", 20,"12/03/2020", 14, 100, "Description");
    }

    @Test
    public void testGetAvailableSeatsNoReservedSeats() {
        eventModel.setReserved_seats(0);
        assertEquals(100, eventModel.getAvailableSeats());
    }

    @Test
    public void testGetAvailableSeatsReservedSome() {
        eventModel.setReserved_seats(30);
        assertEquals(70, eventModel.getAvailableSeats());
    }

    @Test
    public void testGetAvailableSeatsReservedMoreThanAvailable() {
        eventModel.setReserved_seats(120);
        assertEquals(0, eventModel.getAvailableSeats());
    }

    @Test
    public void testSetReservedSeats() {
        eventModel.setReserved_seats(140);
        assertEquals(100, eventModel.getReserved_seats());

        eventModel.setReserved_seats(50);
        assertEquals(50, eventModel.getReserved_seats());

        eventModel.setReserved_seats(0);
        assertEquals(0, eventModel.getReserved_seats());
    }

    @Test
    public void testAddReservedSeats() {
        eventModel.setReserved_seats(30);

        eventModel.addReservedSeats(10);
        assertEquals(60, eventModel.getAvailableSeats());

        eventModel.addReservedSeats(0);
        assertEquals(60, eventModel.getAvailableSeats());

        eventModel.addReservedSeats(70);
        assertEquals(0, eventModel.getAvailableSeats());
    }
}