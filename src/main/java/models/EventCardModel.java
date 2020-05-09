package models;

import services.ServiceProvider;
import services.UserService;

public class EventCardModel {
    private String barName;
    private String artistName;
    private EventModel eventModel;

    public EventCardModel(EventModel eventModel) {
        this.eventModel = eventModel;
        UserService userService = ServiceProvider.getUserService();
        barName = userService.getUser(eventModel.getBar_manager_id()).getName();
        artistName = userService.getUser(eventModel.getArtist_id()).getName();
    }

    public String getBarName() {
        return barName;
    }

    public String getArtistName() {
        return artistName;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

    public boolean containsFilter(String filter) {
        return getEventModel().getName().contains(filter) ||
                getArtistName().contains(filter) ||
                getBarName().contains(filter);
    }

}
