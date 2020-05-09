package services;

import models.EventModel;

import java.util.List;

public interface EventService {
    EventModel getEventUsingEventId(int id);

    EventModel getEventUsingEventName(String name);

    List<EventModel> getEventsUsingBarId(int bar_manager_id);

    List<EventModel> getEventsUsingArtistId(int artist_id);

    List<EventModel> getEventsWithDate(String date);

    void updateEvent(EventModel model);

    void createEvent(EventModel artistModel);

    List<EventModel> getAllEvents();
}
