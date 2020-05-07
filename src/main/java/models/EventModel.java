package models;

public class EventModel {
    private String eventName;
    private String barName;
    private String artistName;

    public EventModel(String eventName, String barName, String artistName) {
        this.eventName = eventName;
        this.barName = barName;
        this.artistName = artistName;
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
}
