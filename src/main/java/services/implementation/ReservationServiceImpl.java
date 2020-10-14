package services.implementation;

import models.EventModel;
import models.ReservationModel;
import repository.IRepository;
import services.IReservationService;
import services.ServiceProvider;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements IReservationService {

    private final IRepository<ReservationModel> reservationRepository;
    private final IRepository<EventModel> eventRepository;

    public ReservationServiceImpl(IRepository<ReservationModel> reservationRepository, IRepository<EventModel> eventRepository) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<ReservationModel> getReservationUsingEventId(int event_id) {
        List<ReservationModel> allReservations = getAllReservations();
        List<ReservationModel> searchResults = new ArrayList<>();

        if (allReservations == null)
            return searchResults;

        for (ReservationModel reservation : allReservations)
            if (reservation.getEvent_id() == event_id)
                searchResults.add(reservation);
        return searchResults;
    }

    @Override
    public List<ReservationModel> getReservationUsingUserId(int user_id) {
        List<ReservationModel> allReservations = getAllReservations();
        List<ReservationModel> searchResults = new ArrayList<>();

        if (allReservations == null)
            return searchResults;

        for (ReservationModel reservation : allReservations)
            if (reservation.getUser_id() == user_id)
                searchResults.add(reservation);
        return searchResults;
    }

    public ReservationModel getReservationUsingReservationId(int reservation_id) {
        List<ReservationModel> allReservations = getAllReservations();

        if (allReservations == null)
            return null;

        for (ReservationModel reservation : allReservations)
            if (reservation.getId() == reservation_id)
                return reservation;
        return null;
    }
    @Override
    public List<ReservationModel> getAllReservations() {
        reservationRepository.setDestinationFileName("ReservationModel/");
        return reservationRepository.getAll();
    }

    @Override
    public void makeReservation(int userId, int eventId, int numberOfSeats) throws ReservationNotCreatedException {
        ReservationModel reservationModel = new ReservationModel(0, userId, eventId, numberOfSeats);
        reservationRepository.setDestinationFileName("ReservationModel/");
        ReservationModel reservationCreated = reservationRepository.create(reservationModel);
        if (reservationCreated == null)
            throw new ReservationNotCreatedException();
        EventModel eventModel = ServiceProvider.getEventService().getEventUsingEventId(reservationModel.getEvent_id());
        eventModel.setReserved_seats(eventModel.getReserved_seats() + numberOfSeats);
        eventRepository.update(eventModel);
    }

    @Override
    public void deleteReservation(int reservationId) throws ReservationNotDeletedException {
        ReservationModel reservationModel = getReservationUsingReservationId(reservationId);
        int reservedSeats = reservationModel.getReserved_seats();
        if (reservationModel == null)
            throw new ReservationNotDeletedException();
        reservationRepository.setDestinationFileName("ReservationModel/deleteReservation.php");
        ReservationModel reservationDeleted = reservationRepository.delete(reservationModel);
        if (reservationDeleted == null)
            throw new ReservationNotDeletedException();
        EventModel eventModel = ServiceProvider.getEventService().getEventUsingEventId(reservationModel.getEvent_id());
        eventModel.setReserved_seats(eventModel.getReserved_seats() - reservedSeats);
        eventRepository.update(eventModel);
    }
}
