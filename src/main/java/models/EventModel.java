package models;

import java.util.Objects;

public class EventModel extends EntityModel {
    private final int bar_manager_id;
    private final int artist_id;
    private String name;
    private String date;
    private int start_hour;
    private String description;
    private int total_seats;
    private int reserved_seats;

    public EventModel(int id, int barManagerId, int artistId, String name, String date, int startHour, int totalSeats) {
        super(id);
        this.bar_manager_id = barManagerId;
        this.artist_id = artistId;
        this.name = name;
        this.date = date;
        this.start_hour = startHour;
        this.total_seats = totalSeats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public void setReserved_seats(int reserved_seats) {
        this.reserved_seats = reserved_seats;
    }

    public int getBar_manager_id() {
        return bar_manager_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public String getDescription() {
        return description;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public String getTotal_seats_string() {
        return total_seats + "";
    }

    public int getReserved_seats() {
        return reserved_seats;
    }

    public int getAvailableSeats() {
        return total_seats - reserved_seats;
    }

    public void addReservedSeats(Integer numberOfSeats) {
        reserved_seats += numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventModel that = (EventModel) o;
        return bar_manager_id == that.bar_manager_id &&
                artist_id == that.artist_id &&
                start_hour == that.start_hour &&
                total_seats == that.total_seats &&
                reserved_seats == that.reserved_seats &&
                Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar_manager_id, artist_id, name, date, start_hour, description, total_seats, reserved_seats);
    }
}
