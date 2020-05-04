package models;

import java.util.ArrayList;
import java.util.List;

public class BarModel {

    private int user_id;
    private String address;
    private String path_to_image;
    private List<Interval> intervals;

    public BarModel(int user_id, String address) {
        this.user_id = user_id;
        this.address = address;
        this.path_to_image = "";
        this.intervals = new ArrayList<>();
    }

    public void addInterval(Interval interval) {
        intervals.add(interval);
    }

    public void deleteInterval(DaysOfWeek day) {
        intervals.removeIf(i -> i.getDay().equals(day));
    }

    public void clearInterval() {
        intervals.clear();
    }

    public int getUser_id() {
        return user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }
}
