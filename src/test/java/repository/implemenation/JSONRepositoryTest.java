package repository.implemenation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.BarModel;
import models.EventModel;
import models.ReservationModel;
import models.UserModel;
import models.other.DaysOfWeek;
import models.other.Interval;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import services.IStorageManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JSONRepositoryTest {

    @Mock
    private IStorageManager storageManager;

    @Test
    public void testEmptyGetAll() {
        JSONRepository<UserModel> jsonRepository = new JSONRepository<>(UserModel.class, storageManager);
        when(storageManager.readContent("UserModel.json")).thenReturn("[]");

        List<UserModel> all = jsonRepository.getAll();

        assertEquals("Empty repository", 0, all.size());
    }

    @Test
    public void testNotEmptyGetAll() {
        JSONRepository<BarModel> jsonRepository = new JSONRepository<>(BarModel.class, storageManager);
        List<BarModel> barModels = new ArrayList<>();

        BarModel barModel1 = new BarModel(4, "adresa1");
        barModel1.setPath_to_image("C:/Pictures/poza1.png");
        barModels.add(barModel1);

        barModels.add(new BarModel(1, "adresa2"));

        BarModel barModel2 = new BarModel(2, "adresa5");
        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(DaysOfWeek.Luni, 12, 22));
        intervals.add(new Interval(DaysOfWeek.Marti, 17, 0));
        intervals.add(new Interval(DaysOfWeek.Miercuri, 19, 23));
        barModel2.setIntervals(intervals);
        barModels.add(barModel2);

        barModels.add(new BarModel(6, "adresa6"));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(barModels);
        when(storageManager.readContent("BarModel.json")).thenReturn(json);

        List<BarModel> all = jsonRepository.getAll();

        assertEquals("Empty repository", barModels.size(), all.size());

        for (int i = 0; i < all.size(); i++) {
            BarModel actual = all.get(i);
            BarModel expected = barModels.get(i);
            assertEquals("Same address", expected.getAddress(), actual.getAddress());
            assertEquals("Same path to image", expected.getPath_to_image(), actual.getPath_to_image());
            assertEquals("Same id", expected.getId(), actual.getId());

            assertEquals("Same interval length", expected.getIntervals().size(), actual.getIntervals().size());

            for (int j = 0; j < actual.getIntervals().size(); j++) {
                assertEquals("Same interval", expected.getIntervals().get(j), actual.getIntervals().get(j));
            }
        }
    }

    @Test
    public void testCreate() {
        JSONRepository<EventModel> jsonRepository = new JSONRepository<>(EventModel.class, storageManager);
        EventModel eventModel = new EventModel(2, 4, 5, "Eveniment 1", "2020-06-12", 0, 30);

        List<EventModel> events = new ArrayList<>();
        events.add(new EventModel(4, 5, 6, "Name1", "2021-05-11", 12, 100));
        events.add(new EventModel(3, 3, 5, "Name2", "2022-03-11", 22, 670));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(events);
        when(storageManager.readContent("EventModel.json")).thenReturn(json);

        EventModel resulted = jsonRepository.create(eventModel);

        assertEquals("Not Same models", eventModel, resulted);
    }

    @Test
    public void testCreateInEmptyFile() {
        JSONRepository<EventModel> jsonRepository = new JSONRepository<>(EventModel.class, storageManager);
        EventModel eventModel = new EventModel(8, 22, 415, "Eveniment 15", "2020-06-22", 22, 300);

        when(storageManager.readContent("EventModel.json")).thenReturn("[]");
        EventModel resulted = jsonRepository.create(eventModel);
        assertEquals("Not Same models", eventModel, resulted);

        when(storageManager.readContent("EventModel.json")).thenReturn(null);
        resulted = jsonRepository.create(eventModel);
        assertEquals("Not Same models", eventModel, resulted);
    }

    @Test
    public void testUpdate() {
        JSONRepository<ReservationModel> jsonRepository = new JSONRepository<>(ReservationModel.class, storageManager);
        ReservationModel reservationModel = new ReservationModel(4, 2, 6, 100);

        List<ReservationModel> reservations = new ArrayList<>();
        reservations.add(new ReservationModel(6, 5, 11, 44));
        reservations.add(new ReservationModel(4, 2, 6, 100));
        reservations.add(new ReservationModel(114, 214, 649, 1700));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(reservations);
        when(storageManager.readContent("ReservationModel.json")).thenReturn(json);

        ReservationModel resulted = jsonRepository.update(reservationModel);

        assertEquals("Not Same models", reservationModel, resulted);
    }

    @Test
    public void testUpdateNotInFile() {
        JSONRepository<ReservationModel> jsonRepository = new JSONRepository<>(ReservationModel.class, storageManager);
        ReservationModel reservationModel = new ReservationModel(4, 2, 6, 100);

        List<ReservationModel> reservations = new ArrayList<>();
        reservations.add(new ReservationModel(6, 5, 11, 44));
        reservations.add(new ReservationModel(7, 2, 1, 100));
        reservations.add(new ReservationModel(114, 214, 649, 1700));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String json = gson.toJson(reservations);
        when(storageManager.readContent("ReservationModel.json")).thenReturn(json);

        ReservationModel resulted = jsonRepository.update(reservationModel);

        assertNull("Not Same models", resulted);
    }

    @Test
    public void testUpdateInEmptyFile() {
        JSONRepository<ReservationModel> jsonRepository = new JSONRepository<>(ReservationModel.class, storageManager);
        ReservationModel reservationModel = new ReservationModel(4, 2, 6, 100);

        when(storageManager.readContent("ReservationModel.json")).thenReturn(null);

        ReservationModel resulted = jsonRepository.update(reservationModel);

        assertNull("Not Same models", resulted);
    }
}