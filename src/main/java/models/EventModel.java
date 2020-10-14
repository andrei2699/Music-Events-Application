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
    private int price;

    public EventModel(int id, int barManagerId, int artistId, String name, int price, String date, int startHour, int totalSeats, String description) {
        super(id);
        this.bar_manager_id = barManagerId;
        this.artist_id = artistId;
        this.name = name;
        this.price = price;
        this.date = date;
        this.start_hour = startHour;
        this.total_seats = totalSeats;
        this.description = description;
    }

    public EventModel(int id, int barManagerId, int artistId, String name, int price, String date, int startHour, int totalSeats) {
        this(id, barManagerId, artistId, name, price, date, startHour, totalSeats, "");
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
        this.reserved_seats = Math.min(reserved_seats, getTotal_seats());
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getPrice() {
        return price;
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
        if (reserved_seats >= total_seats)
            reserved_seats = total_seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventModel that = (EventModel) o;
        return bar_manager_id == that.bar_manager_id &&
                artist_id == that.artist_id &&
                start_hour == that.start_hour &&
                price == that.price &&
                total_seats == that.total_seats &&
                reserved_seats == that.reserved_seats &&
                Objects.equals(name, that.name) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bar_manager_id, artist_id, name, price, date, start_hour, description, total_seats, reserved_seats);
    }
}
