package services;

import models.ReservationModel;
import services.implementation.ReservationNotCreatedException;

import java.util.List;

public interface IReservationService {
    List<ReservationModel> getReservationUsingEventId(int event_id);

    List<ReservationModel> getReservationUsingUserId(int user_id);

    List<ReservationModel> getAllReservations();

    void makeReservation(int userId, int eventId, int numberOfSeats) throws ReservationNotCreatedException;
}
