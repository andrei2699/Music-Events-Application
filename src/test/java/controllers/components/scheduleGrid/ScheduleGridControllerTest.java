package controllers.components.scheduleGrid;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.other.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static main.ApplicationResourceStrings.*;
import static models.other.DaysOfWeek.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleGridControllerTest extends ApplicationTest {

    @Mock
    private ScheduleGridLoadStrategy strategy;

    private ScheduleGridController scheduleGridController;

    @Before
    public void setUp() {
        scheduleGridController = new ScheduleGridController();
        scheduleGridController.scheduleGridPane = new GridPane();
    }

    @Test
    public void testInitialize() {
        scheduleGridController.initialize(null, null);
        assertEquals((NumberOfDays + 1) * 3, scheduleGridController.scheduleGridPane.getChildren().size());
        assertEquals(Label.class, scheduleGridController.gridHBoxes[0][0].getChildren().get(0).getClass());
        assertEquals(Label.class, scheduleGridController.gridHBoxes[0][1].getChildren().get(0).getClass());
        assertEquals(Label.class, scheduleGridController.gridHBoxes[0][2].getChildren().get(0).getClass());

        assertEquals(DAY_TEXT, ((Label) scheduleGridController.gridHBoxes[0][0].getChildren().get(0)).getText());
        assertEquals(START_TEXT, ((Label) scheduleGridController.gridHBoxes[0][1].getChildren().get(0)).getText());
        assertEquals(END_TEXT, ((Label) scheduleGridController.gridHBoxes[0][2].getChildren().get(0)).getText());
    }

    @Test
    public void testGetIntervalsFromGrid() {
        List<Interval> intervalList = new ArrayList<>();
        intervalList.add(new Interval(Luni, 14, 22));
        intervalList.add(new Interval(Vineri, 18, 23));
        intervalList.add(new Interval(Sambata, 19, 23));
        scheduleGridController.initialize(null, null);
        scheduleGridController.setLoadStrategy(strategy);

        when(strategy.getIntervalsFromGrid(scheduleGridController.gridHBoxes)).thenReturn(intervalList);
        assertEquals(intervalList, scheduleGridController.getIntervalsFromGrid());
    }
}