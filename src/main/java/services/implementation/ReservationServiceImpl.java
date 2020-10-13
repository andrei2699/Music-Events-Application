package services.implementation;

import models.EventModel;
import models.ReservationModel;
import repository.IRepository;
import services.IReservationService;

import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements IReservationService {

    private final IRepository<ReservationModel> reservationRepository;

    public ReservationServiceImpl(IRepository<ReservationModel> reservationRepository) {
        this.reservationRepository = reservationRepository;
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

    @Override
    public List<ReservationModel> getAllReservations() {
        reservationRepository.setDestinationFileName("ReservationModel/");
        return reservationRepository.getAll();
    }

    @Override
    public void makeReservation(int userId, int eventId, int numberOfSeats) throws ReservationNotCreatedException {
        ReservationModel reservationModel = new ReservationModel(0, userId, eventId, numberOfSeats);
        ReservationModel reservationCreated = reservationRepository.create(reservationModel);
        if (reservationCreated == null)
            throw new ReservationNotCreatedException();
    }
}
