package models;

public class ReservationModel extends EntityModel {
    private final int user_id;
    private final int event_id;
    private final int reserved_seats;

    public ReservationModel(int reservation_id, int user_id, int event_id, int reserved_seats) {
        super(reservation_id);
        this.user_id = user_id;
        this.event_id = event_id;
        this.reserved_seats = reserved_seats;
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
