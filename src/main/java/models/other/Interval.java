package models.other;

import models.other.DaysOfWeek;

public class Interval {

    private DaysOfWeek day;
    private Integer start_hour;
    private Integer end_hour;

    public Interval(DaysOfWeek day, Integer start_hour, Integer end_hour) {
        this.day = day;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    public DaysOfWeek getDay() {
        return day;
    }

    public void setDay(DaysOfWeek day) {
        this.day = day;
    }

    public Integer getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(Integer start_hour) {
        this.start_hour = start_hour;
    }

    public Integer getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(Integer end_hour) {
        this.end_hour = end_hour;
    }
}
