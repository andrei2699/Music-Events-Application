package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.EventModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EventService {
    EventModel getEventUsingEventId(int id);

    EventModel getEventUsingEventName(String name);

    List<EventModel> getEventsUsingBarId(int bar_manager_id);

    List<EventModel> getEventsUsingArtistId(int artist_id);

    List<EventModel> getEventsStartingFrom(LocalDate date, int startHour);

    void updateEvent(EventModel model);

    void createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description);

    List<EventModel> getAllEvents();
}
