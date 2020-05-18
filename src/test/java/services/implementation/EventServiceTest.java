package services.implementation;

import models.EventModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IEventService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @Mock
    private IRepository<EventModel> repository;

    private IEventService eventService;

    private List<EventModel> dummyEventModels;

    @Before
    public void setUp() {
        eventService = new EventServiceImpl(repository);

        dummyEventModels = new ArrayList<>();
        dummyEventModels.add(new EventModel(1, 2, 3, "Event 1", "2020-08-12", 12, 400, "descriere 1"));
        dummyEventModels.add(new EventModel(2, 6, 1, "Event 2", "2021-04-11", 18, 50, "descriere 2"));
        dummyEventModels.add(new EventModel(4, 31, 3, "Event 3", "2020-07-12", 19, 44, ""));
        dummyEventModels.add(new EventModel(7, 2, 44, "Event 4", "2022-01-01", 15, 100, "descriere 51"));
        dummyEventModels.add(new EventModel(8, 5, 7, "Event 5", "2020-05-12", 20, 140));
        dummyEventModels.add(new EventModel(9, 1, 4, "Event 6", "2021-04-11", 19, 140, "descriere 1551"));
    }

    @After
    public void tearDown() {
        eventService = null;
        dummyEventModels = null;
    }

    @Test
    public void testGetEventUsingEventId() {
        when(repository.getAll()).thenReturn(dummyEventModels);

        EventModel eventModel = eventService.getEventUsingEventId(1);
        assertEquals("Not same model", dummyEventModels.get(0), eventModel);

        eventModel = eventService.getEventUsingEventId(4);
        assertEquals("Not same model", dummyEventModels.get(2), eventModel);

        eventModel = eventService.getEventUsingEventId(8);
        assertEquals("Not same model", dummyEventModels.get(4), eventModel);

        eventModel = eventService.getEventUsingEventId(123);
        assertNull("Not in file", eventModel);


        when(repository.getAll()).thenReturn(null);

        eventModel = eventService.getEventUsingEventId(7);
        assertNull("Not null model", eventModel);

        when(repository.getAll()).thenReturn(new ArrayList<>());
        eventModel = eventService.getEventUsingEventId(41);
        assertNull("Not null model", eventModel);
    }

    @Test
    public void testGetEventUsingEventName() {
        when(repository.getAll()).thenReturn(dummyEventModels);

        EventModel eventModel = eventService.getEventUsingEventName("Event 1");
        assertEquals("Not same model", dummyEventModels.get(0), eventModel);

        eventModel = eventService.getEventUsingEventName("Event 3");
        assertEquals("Not same model", dummyEventModels.get(2), eventModel);

        eventModel = eventService.getEventUsingEventName("Event 5");
        assertEquals("Not same model", dummyEventModels.get(4), eventModel);

        eventModel = eventService.getEventUsingEventName("New Event Name");
        assertNull("Not in file", eventModel);


        when(repository.getAll()).thenReturn(null);

        eventModel = eventService.getEventUsingEventName("Not existing Event Name");
        assertNull("Not null model", eventModel);

        when(repository.getAll()).thenReturn(new ArrayList<>());
        eventModel = eventService.getEventUsingEventName("Name");
        assertNull("Not null model", eventModel);
    }

    @Test
    public void testGetEventsUsingBarId() {
        when(repository.getAll()).thenReturn(dummyEventModels);

        List<EventModel> events = eventService.getEventsUsingBarId(2);
        assertEquals("Not same size", 2, events.size());
        assertEquals("Not same model", dummyEventModels.get(0), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(3), events.get(1));

        events = eventService.getEventsUsingBarId(6);
        assertEquals("Not same size", 1, events.size());
        assertEquals("Not same model", dummyEventModels.get(1), events.get(0));

        events = eventService.getEventsUsingBarId(141);
        assertEquals("Not same size", 0, events.size());

        when(repository.getAll()).thenReturn(null);

        events = eventService.getEventsUsingBarId(31);
        assertEquals("Not same size", 0, events.size());

        when(repository.getAll()).thenReturn(new ArrayList<>());
        events = eventService.getEventsUsingBarId(63);
        assertEquals("Not same size", 0, events.size());
    }

    @Test
    public void testGetEventsUsingArtistId() {
        when(repository.getAll()).thenReturn(dummyEventModels);

        List<EventModel> events = eventService.getEventsUsingArtistId(3);
        assertEquals("Not same size", 2, events.size());
        assertEquals("Not same model", dummyEventModels.get(0), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(2), events.get(1));

        events = eventService.getEventsUsingArtistId(44);
        assertEquals("Not same size", 1, events.size());
        assertEquals("Not same model", dummyEventModels.get(3), events.get(0));

        events = eventService.getEventsUsingArtistId(651);
        assertEquals("Not same size", 0, events.size());

        when(repository.getAll()).thenReturn(null);

        events = eventService.getEventsUsingArtistId(7);
        assertEquals("Not same size", 0, events.size());


        when(repository.getAll()).thenReturn(new ArrayList<>());
        events = eventService.getEventsUsingArtistId(17);
        assertEquals("Not same size", 0, events.size());
    }

    @Test
    public void testGetEventsStartingFromInCorrectOrder() {
        when(repository.getAll()).thenReturn(null);
        try {
            eventService.getEventsStartingFrom(LocalDate.parse("2024-09-24"), 15);
        } catch (NullPointerException e) {
            fail();
        }

        when(repository.getAll()).thenReturn(dummyEventModels);

        List<EventModel> events = eventService.getEventsStartingFrom(LocalDate.parse("2020-01-05"), 12);

        List<EventModel> sortedEvents = new ArrayList<>(dummyEventModels);
        sortedEvents.sort((event1, event2) -> {
            if (event1.getDate().equals(event2.getDate())) {
                return event1.getStart_hour() - event2.getStart_hour();
            }
            return event1.getDate().compareTo(event2.getDate());
        });

        assertEquals("Not same size", sortedEvents.size(), events.size());
        for (int i = 0; i < events.size(); i++) {
            assertEquals("Not same model", sortedEvents.get(i), events.get(i));
        }
    }

    @Test
    public void testGetEventsStartingFrom() {
        when(repository.getAll()).thenReturn(dummyEventModels);

        List<EventModel> events = eventService.getEventsStartingFrom(LocalDate.parse("2023-03-05"), 16);
        assertEquals("Not same size", 0, events.size());


        events = eventService.getEventsStartingFrom(LocalDate.parse("2020-07-12"), 6);
        assertEquals("Not same size", 5, events.size());
        assertEquals("Not same model", dummyEventModels.get(2), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(0), events.get(1));
        assertEquals("Not same model", dummyEventModels.get(1), events.get(2));
        assertEquals("Not same model", dummyEventModels.get(5), events.get(3));
        assertEquals("Not same model", dummyEventModels.get(3), events.get(4));

        events = eventService.getEventsStartingFrom(LocalDate.parse("2020-07-12"), 19);
        assertEquals("Not same size", 4, events.size());
        assertEquals("Not same model", dummyEventModels.get(0), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(1), events.get(1));
        assertEquals("Not same model", dummyEventModels.get(5), events.get(2));
        assertEquals("Not same model", dummyEventModels.get(3), events.get(3));

        events = eventService.getEventsStartingFrom(LocalDate.parse("2020-07-12"), 20);
        assertEquals("Not same size", 4, events.size());
        assertEquals("Not same model", dummyEventModels.get(0), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(1), events.get(1));
        assertEquals("Not same model", dummyEventModels.get(5), events.get(2));
        assertEquals("Not same model", dummyEventModels.get(3), events.get(3));

        events = eventService.getEventsStartingFrom(LocalDate.parse("2021-04-11"), 18);
        assertEquals("Not same size", 2, events.size());
        assertEquals("Not same model", dummyEventModels.get(5), events.get(0));
        assertEquals("Not same model", dummyEventModels.get(3), events.get(1));
    }

    @Test
    public void testCreateEvent() {
        EventModel dummyEventModel = new EventModel(10, 2, 5, "Eveniment nou", "2020-07-30", 14, 520, "Descrierea evenimentului");
        when(repository.getAll()).thenReturn(dummyEventModels);
        when(repository.create(dummyEventModel)).thenReturn(dummyEventModel);
        assertEquals(dummyEventModel, eventService.createEvent(2, 5, "Eveniment nou", "2020-07-30", 14, 520, "Descrierea evenimentului"));

        when(repository.getAll()).thenReturn(null);
        try {
            eventService.createEvent(6, 14, "Event nou", "2020-04-26", 12, 600, "Descrierea evenimentului");
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testGetAllEvents() {
        when(repository.getAll()).thenReturn(dummyEventModels);
        List<EventModel> allEvents = eventService.getAllEvents();
        assertEquals("Not same list", dummyEventModels, allEvents);
    }
}
