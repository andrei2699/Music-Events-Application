package services;

import models.ReservationModel;

import java.util.List;

public interface ReservationService {
    List<ReservationModel> getReservationUsingUserId(int user_id);

    List<ReservationModel> getAllReservations();

    void makeReservation(int userId, int eventId, int numberOfSeats);
}
