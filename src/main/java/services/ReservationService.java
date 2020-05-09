package services;

public interface ReservationService {
    void makeReservation(int userId, int eventId, int numberOfSeats);
}
