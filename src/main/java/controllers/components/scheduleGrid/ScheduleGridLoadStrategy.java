package controllers.components.scheduleGrid;

import javafx.scene.layout.HBox;
import models.other.Interval;

import java.util.List;

public interface ScheduleGridLoadStrategy {
    void fillScheduleGridPane(HBox[][] gridHBoxes, List<Interval> intervals);

    List<Interval> getIntervalsFromGrid(HBox[][] gridHBoxes);
}