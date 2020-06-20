package controllers.components.scheduleGrid;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.other.DaysOfWeek;
import models.other.Interval;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static main.ApplicationResourceStrings.*;
import static models.other.DaysOfWeek.NumberOfDays;

public class ScheduleGridController implements Initializable {
    @FXML
    public GridPane scheduleGridPane;

    @FXML
    public VBox gridVBox;

    protected HBox[][] gridHBoxes;
    protected ScheduleGridLoadStrategy loadStrategy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gridHBoxes = new HBox[NumberOfDays + 1][3]; // 3 = day of week, start_hour, end_hour


        for (int column = 0; column < gridHBoxes.length; column++) {
            for (int row = 0; row < gridHBoxes[column].length; row++) {
                gridHBoxes[column][row] = new HBox();
                gridHBoxes[column][row].setAlignment(Pos.CENTER);
                gridHBoxes[column][row].setPadding(new Insets(0, 2, 0, 2));
                scheduleGridPane.add(gridHBoxes[column][row], column, row);
            }
        }

        gridHBoxes[0][0].getChildren().add(new Label(DAY_TEXT));
        gridHBoxes[0][1].getChildren().add(new Label(START_TEXT));
        gridHBoxes[0][2].getChildren().add(new Label(END_TEXT));

        int dayOfWeekIndex = 1;
        for (DaysOfWeek daysOfWeek : DaysOfWeek.values()) {
            gridHBoxes[dayOfWeekIndex][0].getChildren().add(new Label(daysOfWeek.toString()));
            dayOfWeekIndex++;
        }
    }

    public void setLoadStrategy(ScheduleGridLoadStrategy loadStrategy) {
        this.loadStrategy = loadStrategy;
    }

    public void setIntervals(List<Interval> intervals) {
        fillScheduleGridPane(intervals);
    }

    public void setVisible(boolean visibility) {
        gridVBox.setVisible(visibility);
    }

    public List<Interval> getIntervalsFromGrid() {
        return loadStrategy.getIntervalsFromGrid(gridHBoxes);
    }

    protected void fillScheduleGridPane(List<Interval> intervals) {
        loadStrategy.fillScheduleGridPane(gridHBoxes, intervals);
    }
}
