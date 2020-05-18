package services.implementation;

import models.EventModel;
import repository.IRepository;
import services.IEventService;

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

        for (EventModel event : allEvents) {
            LocalDate parseLocalDate = LocalDate.parse(event.getDate());
            if (parseLocalDate.compareTo(date) > 0) {
                searchResults.add(event);
            } else if (parseLocalDate.compareTo(date) == 0 && event.getStart_hour() > startHour) {
                searchResults.add(event);
            }
        }
        return searchResults;
    }

    @Override
    public void updateEvent(EventModel model) {
        eventRepository.update(model);
    }

    @Override
    public void createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description) {
        List<EventModel> events = getAllEvents();

        if (events == null)
            events = new ArrayList<>();

        int biggestId = -1;
        for (EventModel event : events) {
            if (event.getId() > biggestId) {
                biggestId = event.getId();
            }
        }

        EventModel eventModel = new EventModel(biggestId + 1, bar_id, artist_id, eventName, date, startHour, totalSeats);
        eventModel.setDescription(description);

        eventRepository.create(eventModel);
    }

    @Override
    public List<EventModel> getAllEvents() {
        return eventRepository.getAll();
    }
}
