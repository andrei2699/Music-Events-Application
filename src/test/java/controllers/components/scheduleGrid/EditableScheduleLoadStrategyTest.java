package controllers.components.scheduleGrid;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import models.other.Interval;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static models.other.DaysOfWeek.*;
import static org.junit.Assert.*;

public class EditableScheduleLoadStrategyTest extends ApplicationTest {

    private EditableScheduleLoadStrategy editableScheduleLoadStrategy;
    private HBox[][] gridHBoxes;

    @Before
    public void setUp() {
        editableScheduleLoadStrategy = new EditableScheduleLoadStrategy();
        gridHBoxes = new HBox[NumberOfDays + 1][3];

        for (int column = 0; column < gridHBoxes.length; column++) {
            for (int row = 0; row < gridHBoxes[column].length; row++) {
                gridHBoxes[column][row] = new HBox();
                ComboBox<Integer> comboBox = new ComboBox<>();
                comboBox.setItems(FXCollections.observableArrayList(editableScheduleLoadStrategy.hours));
                gridHBoxes[column][row].getChildren().add(comboBox);
            }
        }
    }

    @Test
    public void testHoursArray() {
        assertEquals(24, editableScheduleLoadStrategy.hours.length);
        for (int i = 0; i < 24; i++) {
            assertEquals(i, editableScheduleLoadStrategy.hours[i].intValue());
        }
    }

    @Test
    public void testGetIntervalsFromGrid() {
        assertNull(editableScheduleLoadStrategy.getIntervalsFromGrid(null));
        assertEquals(7, editableScheduleLoadStrategy.getIntervalsFromGrid(gridHBoxes).size());
    }

    @Test
    public void testFillScheduleGridPane() {
        try {
            editableScheduleLoadStrategy.fillScheduleGridPane(null, null);
        } catch (NullPointerException e) {
            fail();
        }

        List<Interval> intervalList = new ArrayList<>();
        intervalList.add(new Interval(Luni, null, null));
        intervalList.add(new Interval(Marti, 14, 21));
        intervalList.add(new Interval(Miercuri, 18, 20));
        intervalList.add(new Interval(Joi, 19, 22));
        intervalList.add(new Interval(Duminica, null, null));
        intervalList.add(new Interval(Vineri, null, null));
        intervalList.add(new Interval(Sambata, null, null));
        intervalList.add(new Interval(Duminica, null, null));

        editableScheduleLoadStrategy.fillScheduleGridPane(gridHBoxes, intervalList);

        for (int column = 1; column < gridHBoxes.length; column++) {
            for (int row = 1; row < gridHBoxes[column].length; row++) {
                assertEquals(ComboBox.class, gridHBoxes[column][row].getChildren().get(0).getClass());
                ComboBox<?> comboBox = (ComboBox<?>) gridHBoxes[column][row].getChildren().get(0);
                assertEquals(24, comboBox.getItems().size());

                Interval interval = intervalList.get(column - 1);
                if (interval != null) {
                    if (row == 1) {
                        if (interval.getStart_hour() == null) {
                            assertNull(comboBox.getValue());
                        }
                    } else if (row == 2) {
                        if (interval.getEnd_hour() == null) {
                            assertNull(comboBox.getValue());
                        }
                    }
                }
            }
        }
    }
}