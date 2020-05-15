package services;

import models.EventModel;
import models.ReservationModel;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<ReservationModel> getReservationUsingUserId(int user_id);

    List<ReservationModel> getReservationUsingEventId(int event_id);

    List<ReservationModel> getReservationUsingArtistId(int artist_id);

    List<ReservationModel> getReservationUsingBarId(int bar_manager_id);

    List<ReservationModel> getReservationUsingDate(LocalDate date);

    List<ReservationModel> getAllReservations();

    void makeReservation(int userId, int eventId, int numberOfSeats);
}
