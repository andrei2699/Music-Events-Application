package services.implementation;

import models.EventModel;
import models.ReservationModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IReservationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @Mock
    private IRepository<ReservationModel> repository;

    @Mock
    private IRepository<EventModel> eventRepository;

    private IReservationService reservationService;
    private List<ReservationModel> dummyReservationModels;

    @Before

    public void setUp() {
        reservationService = new ReservationServiceImpl(repository,eventRepository);

        dummyReservationModels = new ArrayList<>();
        dummyReservationModels.add(new ReservationModel(1, 2, 5, 166));
        dummyReservationModels.add(new ReservationModel(2, 6, 42, 1000));
        dummyReservationModels.add(new ReservationModel(3, 27, 37, 200));
        dummyReservationModels.add(new ReservationModel(4, 16, 62, 510));
        dummyReservationModels.add(new ReservationModel(5, 58, 42, 5745));
        dummyReservationModels.add(new ReservationModel(6, 16, 61, 520));
    }


    @Test
    public void testGetAllReservations() {
        when(repository.getAll()).thenReturn(dummyReservationModels);
        assertEquals(dummyReservationModels, reservationService.getAllReservations());
    }

    @Test
    public void testGetReservationUsingEventId() {
        when(repository.getAll()).thenReturn(dummyReservationModels);

        List<ReservationModel> reservations = reservationService.getReservationUsingEventId(42);
        assertEquals(2, reservations.size());
        assertEquals(dummyReservationModels.get(1), reservations.get(0));
        assertEquals(dummyReservationModels.get(4), reservations.get(1));

        reservations = reservationService.getReservationUsingEventId(37);
        assertEquals(1, reservations.size());
        assertEquals(dummyReservationModels.get(2), reservations.get(0));

        reservations = reservationService.getReservationUsingEventId(763);
        assertEquals(0, reservations.size());

        when(repository.getAll()).thenReturn(null);
        assertEquals(0, reservationService.getReservationUsingEventId(54).size());
    }

    @Test
    public void testGetReservationUsingUserId() {
        when(repository.getAll()).thenReturn(dummyReservationModels);

        List<ReservationModel> reservations = reservationService.getReservationUsingUserId(16);
        assertEquals(2, reservations.size());
        assertEquals(dummyReservationModels.get(3), reservations.get(0));
        assertEquals(dummyReservationModels.get(5), reservations.get(1));

        reservations = reservationService.getReservationUsingUserId(6);
        assertEquals(1, reservations.size());
        assertEquals(dummyReservationModels.get(1), reservations.get(0));

        reservations = reservationService.getReservationUsingUserId(50);
        assertEquals(0, reservations.size());

        when(repository.getAll()).thenReturn(null);
        assertEquals(0, reservationService.getReservationUsingUserId(81).size());
    }

    @Test
    public void testMakeReservation() {
        when(repository.getAll()).thenReturn(null);
        try {
            reservationService.makeReservation(5, 12, 100);
        } catch (NullPointerException | ReservationNotCreatedException e) {
            fail();
        }
    }
}
