package controllers.components.scheduleGrid;

import javafx.scene.layout.HBox;
import models.other.Interval;

import java.util.List;

public abstract class ScheduleGridLoadStrategy {
    protected final Integer[] hours;

    public ScheduleGridLoadStrategy() {
        hours = new Integer[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = i;
        }
    }

    public abstract void fillScheduleGridPane(HBox[][] gridHBoxes, List<Interval> intervals);

    public abstract List<Interval> getIntervalsFromGrid(HBox[][] gridHBoxes);
}