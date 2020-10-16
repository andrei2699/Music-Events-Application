package services.implementation;

import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import repository.IRepository;
import services.IEventService;
import services.ServiceProvider;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements IEventService {

    private final IRepository<EventModel> eventRepository;

    public EventServiceImpl(IRepository<EventModel> eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventModel getEventUsingEventId(int id) {
        List<EventModel> allEvents = getAllEvents();
        if (allEvents == null)
            return null;
        return allEvents.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public EventModel getEventUsingEventName(String name) {
        List<EventModel> allEvents = getAllEvents();
        if (allEvents == null)
            return null;
        return allEvents.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<EventModel> getEventsUsingBarId(int bar_manager_id) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();

        if (allEvents == null)
            return searchResults;

        for (EventModel event : allEvents)
            if (event.getBar_manager_id() == bar_manager_id)
                searchResults.add(event);
        return searchResults;
    }

    @Override
    public List<EventModel> getEventsUsingArtistId(int artist_id) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();

        if (allEvents == null)
            return searchResults;

        for (EventModel event : allEvents)
            if (event.getArtist_id() == artist_id)
                searchResults.add(event);
        return searchResults;
    }

    @Override
    public List<EventModel> getEventsStartingFrom(LocalDate date, int startHour) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();

        if (allEvents == null)
            return searchResults;

        for (EventModel event : allEvents) {
            LocalDate parseLocalDate = LocalDate.parse(event.getDate());
            if (parseLocalDate.compareTo(date) > 0) {
                searchResults.add(event);
            } else if (parseLocalDate.compareTo(date) == 0 && event.getStart_hour() > startHour) {
                searchResults.add(event);
            }
        }

        searchResults.sort((event1, event2) -> {
            if (event1.getDate().equals(event2.getDate())) {
                return event1.getStart_hour() - event2.getStart_hour();
            }
            return event1.getDate().compareTo(event2.getDate());
        });
        return searchResults;
    }

    @Override
    public void updateEvent(EventModel model) {
        eventRepository.setDestinationFileName("EventModel/");
        eventRepository.update(model);
    }

    @Override
    public EventModel createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description) throws EventNotCreatedException {
        EventModel eventModel = new EventModel(0, bar_id, artist_id, eventName, date, startHour, totalSeats, description);
        eventRepository.setDestinationFileName("EventModel/createEvent.php");
        EventModel eventCreated = eventRepository.create(eventModel);
        if (eventCreated == null)
            throw new EventNotCreatedException();
        return getEventUsingEventName(eventName);
    }

    @Override
    public void deleteEvent(int eventId) throws EventNotDeletedException {
        EventModel eventModel = getEventUsingEventId(eventId);
        if (eventModel == null)
            throw new EventNotDeletedException();
        eventRepository.setDestinationFileName("EventModel/deleteEvent.php");
        EventModel eventDeleted = eventRepository.delete(eventModel);
        if (eventDeleted == null)
            throw new EventNotDeletedException();
    }


    @Override
    public List<EventModel> getAllEvents() {
        eventRepository.setDestinationFileName("EventModel/");
        return eventRepository.getAll();
    }
}
