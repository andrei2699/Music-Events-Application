package services.implementation;

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
        for (ReservationModel reservation : allReservations)
            if (reservation.getEvent_id() == event_id)
                searchResults.add(reservation);
        return searchResults;
    }

    @Override
    public List<ReservationModel> getReservationUsingUserId(int user_id) {
        List<ReservationModel> allReservations = getAllReservations();
        List<ReservationModel> searchResults = new ArrayList<>();
        for (ReservationModel reservation : allReservations)
            if (reservation.getUser_id() == user_id)
                searchResults.add(reservation);
        return searchResults;
    }

    @Override
    public List<ReservationModel> getAllReservations() {
        return reservationRepository.getAll();
    }

    @Override
    public void makeReservation(int userId, int eventId, int numberOfSeats) {
        List<ReservationModel> reservations = getAllReservations();

        int biggestId = -1;
        for (ReservationModel reservation : reservations) {
            if (reservation.getId() > biggestId) {
                biggestId = reservation.getId();
            }
        }

        ReservationModel reservationModel = new ReservationModel(biggestId + 1, userId, eventId, numberOfSeats);

        reservationRepository.create(reservationModel);
    }
}
