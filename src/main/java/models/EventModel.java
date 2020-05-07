package models;

public class EventModel {
    private String eventName;
    private String barName;
    private String artistName;
    private String date;
    private String startHour;
    private String description;
    private int numberOfSeats;

    public EventModel(String eventName, String barName, String artistName, String date, String startHour, int numberOfSeats) {
        this.eventName = eventName;
        this.barName = barName;
        this.artistName = artistName;
        this.date = date;
        this.startHour = startHour;
        this.numberOfSeats = numberOfSeats;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }
}
