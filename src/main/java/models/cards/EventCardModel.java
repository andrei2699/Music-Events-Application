package models.cards;

import models.EventModel;
import services.IUserService;
import services.ServiceProvider;

public class EventCardModel implements TableCardModel {
    private final String barName;
    private final String artistName;
    private final EventModel eventModel;

    public EventCardModel(EventModel eventModel) {
        this(eventModel, ServiceProvider.getUserService());
    }

    public EventCardModel(EventModel eventModel, IUserService userService) {
        this.eventModel = eventModel;
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
        if (filter == null)
            return true;
        return getEventModel().getName().toLowerCase().contains(filter.toLowerCase()) ||
                getArtistName().toLowerCase().contains(filter.toLowerCase()) ||
                getBarName().toLowerCase().contains(filter.toLowerCase());
    }

    public EventCardModel getEventCardModel() {
        return this;
    }
}
