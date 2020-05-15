package models;

public class ReservationModel {
    private int reservation_id;
    private int user_id;
    private int event_id;
    private int reserved_seats;

    public ReservationModel(int reservation_id, int user_id, int event_id, int reserved_seats) {
        this.reservation_id = reservation_id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.reserved_seats = reserved_seats;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getReserved_seats() {
        return reserved_seats;
    }
}
