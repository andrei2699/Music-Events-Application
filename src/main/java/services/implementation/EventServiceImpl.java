package services.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.EventModel;
import models.cards.EventCardModel;
import models.cards.TableCardModel;
import services.EventService;
import services.FileSystemManager;
import services.ServiceProvider;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {
    @Override
    public EventModel getEventUsingEventId(int id) {
        List<EventModel> allEvents = getAllEvents();
        return allEvents.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    @Override
    public EventModel getEventUsingEventName(String name) {
        List<EventModel> allEvents = getAllEvents();
        return allEvents.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<EventModel> getEventsUsingBarId(int bar_manager_id) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();
        for (EventModel event : allEvents)
            if (event.getBar_manager_id() == bar_manager_id)
                searchResults.add(event);
        return searchResults;
    }

    @Override
    public List<EventModel> getEventsUsingArtistId(int artist_id) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();
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
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path eventsFilePath = fileSystemManager.getEventsFilePath();
        List<EventModel> events = getAllEvents();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (EventModel event : events) {
            if (event.getId() == model.getId()) {
                event.setDate(model.getDate());
                event.setDescription(model.getDescription());
                event.setName(model.getName());
                event.setStart_hour(model.getStart_hour());
                event.setTotal_seats(model.getTotal_seats());
                event.setReserved_seats(model.getReserved_seats());
                break;
            }
        }

        String json = gson.toJson(events);
        fileSystemManager.writeToFile(eventsFilePath, json);
    }

    @Override
    public void createEvent(int bar_id, int artist_id, String eventName, String date, int startHour, int totalSeats, String description) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path eventsFilePath = fileSystemManager.getEventsFilePath();
        List<EventModel> events = getAllEvents();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        int biggestId = -1;
        for (EventModel event : events) {
            if (event.getId() > biggestId) {
                biggestId = event.getId();
            }
        }

        EventModel eventModel = new EventModel(biggestId + 1, bar_id, artist_id, eventName, date, startHour, totalSeats);
        eventModel.setDescription(description);

        events.add(eventModel);

        String json = gson.toJson(events);
        fileSystemManager.writeToFile(eventsFilePath, json);
    }

    @Override
    public List<EventModel> getAllEvents() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path eventsFilePath = fileSystemManager.getEventsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(eventsFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<EventModel>>() {
        }.getType());
    }
}
