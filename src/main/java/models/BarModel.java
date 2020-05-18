package models;

import models.other.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BarModel extends EntityModel {

    private String address;
    private String path_to_image;
    private List<Interval> intervals;

    public BarModel(int user_id, String address) {
        super(user_id);
        this.address = address;
        this.path_to_image = "";
        this.intervals = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarModel barModel = (BarModel) o;
        return Objects.equals(address, barModel.address) &&
                Objects.equals(path_to_image, barModel.path_to_image) &&
                Objects.equals(intervals, barModel.intervals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, path_to_image, intervals);
    }
}
