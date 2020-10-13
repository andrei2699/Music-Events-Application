package services;

import models.EventModel;
import services.implementation.EventNotCreatedException;

import java.time.LocalDate;
import java.util.List;

public interface IEventService {
    EventModel getEventUsingEventId(int id);

    EventModel getEventUsingEventName(String name);

    List<EventModel> getEventsUsingBarId(int bar_manager_id);

    List<EventModel> getEventsUsingArtistId(int artist_id);

    List<EventModel> getEventsStartingFrom(LocalDate date, int startHour);

    void updateEvent(EventModel model);

    EventModel createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description) throws EventNotCreatedException;

    List<EventModel> getAllEvents();
}
