package services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.EventModel;
import services.FileSystemManager;
import services.ServiceProvider;
import services.interfaces.EventService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {

    public EventServiceImpl() {
    }

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
    public List<EventModel> getEventsWithDate(String date) {
        List<EventModel> allEvents = getAllEvents();
        List<EventModel> searchResults = new ArrayList<>();
        for (EventModel event : allEvents)
            if (event.getDate().equals(date))
                searchResults.add(event);
        return searchResults;
    }

    @Override
    public void updateEvent(EventModel model) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path eventsFilePath = fileSystemManager.getEventsFilePath();
        List<EventModel> events = getAllEvents();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        for (EventModel event : events) {
            if (event.getId() == model.getId()) {
                event.setDate(model.getDate());
                event.setDescription(model.getDescription());
                event.setName(model.getName());
                event.setStart_hour(model.getStart_hour());
                event.setTotal_seats(event.getTotal_seats());
                break;
            }
        }

        String json = gson.toJson(events);
        fileSystemManager.writeToFile(eventsFilePath, json);
    }

    @Override
    public void createEvent(EventModel eventModel) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path eventsFilePath = fileSystemManager.getEventsFilePath();
        List<EventModel> events = getAllEvents();
        boolean found = false;

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (EventModel event : events) {
            if (event.getId() == eventModel.getId()) {
                found = true;
                updateEvent(eventModel);
                break;
            }
        }

        if (!found) {
            events.add(eventModel);

            String json = gson.toJson(events);
            fileSystemManager.writeToFile(eventsFilePath, json);
        }
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
