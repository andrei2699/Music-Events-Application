package services.implementation;

import models.BarModel;
import models.other.DaysOfWeek;
import models.other.Interval;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.IBarService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarServiceTest {

    @Mock
    private IRepository<BarModel> repository;

    private IBarService barService;

    private List<BarModel> barModels;

    @Before
    public void setUp() {
        barService = new BarServiceImpl(repository);

        barModels = new ArrayList<>();
        barModels.add(new BarModel(1, "Bucovinei 13"));
        barModels.add(new BarModel(21, "Mehala 35"));
        barModels.add(new BarModel(12, "Superba 44"));

        BarModel barModel1 = new BarModel(45, "Lunei 76");
        barModel1.setPath_to_image("C:/Imagini/poza1.png");

        List<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(DaysOfWeek.Duminica, 3, 11));
        intervals.add(new Interval(DaysOfWeek.Marti, 16, 21));
        intervals.add(new Interval(DaysOfWeek.Vineri, 18, 23));
        intervals.add(new Interval(DaysOfWeek.Sambata, 1, 22));

        barModel1.setIntervals(intervals);
        barModels.add(barModel1);

        BarModel barModel2 = new BarModel(34, "Matei Basarab 34");
        barModel2.setPath_to_image("C:/Imagini/poza2.png");

        List<Interval> intervals2 = new ArrayList<>();
        intervals2.add(new Interval(DaysOfWeek.Luni, 13, 14));
        intervals2.add(new Interval(DaysOfWeek.Marti, 17, 19));
        intervals2.add(new Interval(DaysOfWeek.Miercuri, 16, 20));
        intervals2.add(new Interval(DaysOfWeek.Joi, 15, 18));

        barModel2.setIntervals(intervals2);
        barModels.add(barModel2);

        BarModel barModel3 = new BarModel(64, "Lunei 112");
        barModel3.setPath_to_image("C:/Imagini/poza3.png");

        List<Interval> intervals3 = new ArrayList<>();
        intervals3.add(new Interval(DaysOfWeek.Duminica, 17, 22));
        intervals3.add(new Interval(DaysOfWeek.Marti, 17, 21));
        intervals3.add(new Interval(DaysOfWeek.Sambata, 14, 23));
        intervals3.add(new Interval(DaysOfWeek.Joi, 21, 23));

        barModel3.setIntervals(intervals3);
        barModels.add(barModel3);
    }

    @After
    public void tearDown() {
        barService = null;
    }

    @Test
    public void testGetAllBars() {
        when(repository.getAll()).thenReturn(barModels);
        List<BarModel> allBars = barService.getAllBars();
        assertEquals("Not the same", barModels, allBars);
    }

    @Test
    public void getBarUsingId() {
        when(repository.getAll()).thenReturn(barModels);
        assertEquals(barModels.get(5), barService.getBar(64));
        assertEquals(barModels.get(2), barService.getBar(12));
        assertEquals(barModels.get(0), barService.getBar(1));
        assertNull(barService.getBar(667));

        when(repository.getAll()).thenReturn(new ArrayList<>());
        assertNull(barService.getBar(12));

        when(repository.getAll()).thenReturn(null);
        assertNull(barService.getBar(21));
    }

    @Test
    public void getBarUsingAddress() {
        when(repository.getAll()).thenReturn(barModels);

        //search for M
        List<BarModel> expected1 = new ArrayList<>();
        expected1.add(barModels.get(1));
        expected1.add(barModels.get(4));

        assertEquals("Found wrong address", expected1, barService.getBars("M"));

        //search for Lunei
        List<BarModel> expected2 = new ArrayList<>();
        expected2.add(barModels.get(3));
        expected2.add(barModels.get(5));

        assertEquals("Found wrong address", expected2, barService.getBars("Lunei"));

        assertEquals(0, barService.getBars("Eneas").size());

        when(repository.getAll()).thenReturn(new ArrayList<>());

        assertEquals(0, barService.getBars("B").size());

        when(repository.getAll()).thenReturn(null);

        assertEquals(0, barService.getBars("Lunei").size());
    }

    @Test
    public void testCreateNewBar() {
        when(repository.getAll()).thenReturn(null);
        try {
            barService.createBar(new BarModel(99, "Stelelor 11"));
        } catch (NullPointerException e) {
            fail("Created Bar in Null List of Bars");
        }
    }
}
