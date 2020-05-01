package models;

public class Interval {

    private String day;
    private String start_hour;
    private String end_hour;

    public Interval(String day, String start_hour, String end_hour) {
        this.day = day;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }
}
