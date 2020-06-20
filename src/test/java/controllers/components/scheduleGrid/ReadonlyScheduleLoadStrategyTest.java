package controllers.components.scheduleGrid;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.other.Interval;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static models.other.DaysOfWeek.*;
import static org.junit.Assert.*;

public class ReadonlyScheduleLoadStrategyTest extends ApplicationTest {
    private ReadonlyScheduleLoadStrategy readonlyScheduleLoadStrategy;
    private HBox[][] gridHBoxes;

    @Before
    public void setUp() {
        readonlyScheduleLoadStrategy = new ReadonlyScheduleLoadStrategy();
        gridHBoxes = new HBox[NumberOfDays + 1][3];

        for (int column = 0; column < gridHBoxes.length; column++) {
            for (int row = 0; row < gridHBoxes[column].length; row++) {
                gridHBoxes[column][row] = new HBox();
                Label comboBox = new Label();
                gridHBoxes[column][row].getChildren().add(comboBox);
            }
        }
    }

    @Test
    public void testHoursArray() {
        assertEquals(24, readonlyScheduleLoadStrategy.hours.length);
        for (int i = 0; i < 24; i++) {
            assertEquals(i, readonlyScheduleLoadStrategy.hours[i].intValue());
        }
    }

    @Test
    public void testGetIntervalsFromGrid() {
        assertNull(readonlyScheduleLoadStrategy.getIntervalsFromGrid(null));
        assertEquals(7, readonlyScheduleLoadStrategy.getIntervalsFromGrid(gridHBoxes).size());
    }

    @Test
    public void testFillScheduleGridPane() {
        try {
            readonlyScheduleLoadStrategy.fillScheduleGridPane(null, null);
        } catch (NullPointerException e) {
            fail();
        }

        List<Interval> intervalList = new ArrayList<>();
        intervalList.add(new Interval(Luni, null, null));
        intervalList.add(new Interval(Marti, 14, 21));
        intervalList.add(new Interval(Miercuri, 14, 20));
        intervalList.add(new Interval(Joi, null, null));
        intervalList.add(new Interval(Vineri, 15, 22));
        intervalList.add(new Interval(Sambata, null, null));
        intervalList.add(new Interval(Duminica, null, null));

        readonlyScheduleLoadStrategy.fillScheduleGridPane(gridHBoxes, intervalList);

        for (int column = 1; column < gridHBoxes.length; column++) {
            for (int row = 1; row < gridHBoxes[column].length; row++) {
                assertEquals(Label.class, gridHBoxes[column][row].getChildren().get(0).getClass());
                Label label = (Label) gridHBoxes[column][row].getChildren().get(0);

                Interval interval = intervalList.get(column - 1);
                if (interval != null) {
                    if (row == 1) {
                        if (interval.getStart_hour() == null) {
                            assertTrue(label.getText().isEmpty());
                        }
                    } else if (row == 2) {
                        if (interval.getEnd_hour() == null) {
                            assertTrue(label.getText().isEmpty());
                        }
                    }
                }
            }
        }
    }
}