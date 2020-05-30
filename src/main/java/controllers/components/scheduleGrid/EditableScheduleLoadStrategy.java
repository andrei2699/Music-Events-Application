package controllers.components.scheduleGrid;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import models.other.DaysOfWeek;
import models.other.Interval;

import java.util.ArrayList;
import java.util.List;

public class EditableScheduleLoadStrategy extends ScheduleGridLoadStrategy {

    @Override
    public List<Interval> getIntervalsFromGrid(HBox[][] gridHBoxes) {
        if (gridHBoxes == null) {
            return null;
        }

        List<Interval> intervals = new ArrayList<>();

        for (DaysOfWeek day : DaysOfWeek.values()) {
            intervals.add(new Interval(day, 0, 0));
        }

        for (int column = 1; column < gridHBoxes.length; column++) {
            ComboBox<?> startHourComboBox = (ComboBox<?>) gridHBoxes[column][1].getChildren().get(0);
            ComboBox<?> endHourComboBox = (ComboBox<?>) gridHBoxes[column][2].getChildren().get(0);

            if (startHourComboBox.getValue() == null) {
                intervals.get(column - 1).setStart_hour(null);
            } else {
                intervals.get(column - 1).setStart_hour(Integer.parseInt(startHourComboBox.getValue().toString()));
            }

            if (endHourComboBox.getValue() == null) {
                intervals.get(column - 1).setEnd_hour(null);
            } else {
                intervals.get(column - 1).setEnd_hour(Integer.parseInt(endHourComboBox.getValue().toString()));
            }
        }

        return intervals;
    }

    @Override
    public void fillScheduleGridPane(HBox[][] gridHBoxes, List<Interval> intervals) {
        for (int column = 1; column < gridHBoxes.length; column++) {
            for (int row = 1; row < gridHBoxes[column].length; row++) {
                ComboBox<Integer> comboBox = new ComboBox<>();

                if (intervals != null && intervals.size() > 0) {
                    Interval interval = intervals.get(column - 1);
                    if (row == 1 && interval != null) {
                        comboBox.setValue(interval.getStart_hour());
                    } else if (row == 2 && interval != null) {
                        comboBox.setValue(interval.getEnd_hour());
                    }
                }
                comboBox.setItems(FXCollections.observableArrayList(hours));
                gridHBoxes[column][row].getChildren().add(comboBox);
            }
        }
    }
}
