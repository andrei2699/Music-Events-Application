package services;

import models.EventModel;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    EventModel getEventUsingEventId(int id);

    EventModel getEventUsingEventName(String name);

    List<EventModel> getEventsUsingBarId(int bar_manager_id);

    List<EventModel> getEventsUsingArtistId(int artist_id);

    List<EventModel> getEventsWithDate(LocalDate date);

    void updateEvent(EventModel model);

    void createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description);

    List<EventModel> getAllEvents();
}
