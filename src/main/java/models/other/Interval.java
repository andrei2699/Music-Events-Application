package models.other;

import models.other.DaysOfWeek;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return day == interval.day &&
                Objects.equals(start_hour, interval.start_hour) &&
                Objects.equals(end_hour, interval.end_hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, start_hour, end_hour);
    }
}
