package controllers.components.scheduleGrid;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.other.DaysOfWeek;
import models.other.Interval;

import java.util.ArrayList;
import java.util.List;

public class ReadonlyScheduleLoadStrategy extends ScheduleGridLoadStrategy {
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
            Label startHourLabel = (Label) gridHBoxes[column][1].getChildren().get(0);
            Label endHourLabel = (Label) gridHBoxes[column][2].getChildren().get(0);

            if (startHourLabel.getText() == null || startHourLabel.getText().isBlank() || startHourLabel.getText().isEmpty()) {
                intervals.get(column - 1).setStart_hour(null);
            } else {
                intervals.get(column - 1).setStart_hour(Integer.parseInt(startHourLabel.getText()));
            }

            if (endHourLabel.getText() == null || endHourLabel.getText().isEmpty() || endHourLabel.getText().isEmpty()) {
                intervals.get(column - 1).setEnd_hour(null);
            } else {
                intervals.get(column - 1).setEnd_hour(Integer.parseInt(endHourLabel.getText()));
            }
        }

        return intervals;
    }

    @Override
    public void fillScheduleGridPane(HBox[][] gridHBoxes, List<Interval> intervals) {
        if (gridHBoxes == null)
            return;

        for (int column = 1; column < gridHBoxes.length; column++) {
            for (int row = 1; row < gridHBoxes[column].length; row++) {
                Label label = new Label();

                if (intervals != null && intervals.size() > 0 && column - 1 < intervals.size()) {
                    Interval interval = intervals.get(column - 1);
                    if (row == 1 && interval != null) {
                        if (interval.getStart_hour() == null) {
                            label.setText("");
                        } else {
                            label.setText(interval.getStart_hour() + "");
                        }
                    } else if (row == 2 && interval != null) {
                        if (interval.getEnd_hour() == null) {
                            label.setText("");

                        } else {
                            label.setText(interval.getEnd_hour() + "");
                        }
                    }
                }
                gridHBoxes[column][row].getChildren().add(label);
            }
        }
    }
}
