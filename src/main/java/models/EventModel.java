package models;

import java.time.LocalDate;

public class EventModel {
    private final int id;
    private final int bar_manager_id;
    private final int artist_id;
    private String name;
    private String date;
    private int start_hour;
    private String description;
    private int total_seats;
    private int reserved_seats;

    public EventModel(int id, int barManagerId, int artistId, String name, String date, int startHour, int totalSeats) {
        this.id = id;
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

    public int getId() {
        return id;
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
}
