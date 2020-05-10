package controllers;

import models.EventCardModel;
import models.EventModel;

public class EventModelContainer {
    private final EventCardModel eventCardModel;

    public EventModelContainer(EventModel eventModel) {
        this.eventCardModel = new EventCardModel(eventModel);
    }

    public EventCardModel getEventCardModel() {
        return eventCardModel;
    }

    public boolean containsFilter(String filter) {
        return eventCardModel.containsFilter(filter);
    }
}