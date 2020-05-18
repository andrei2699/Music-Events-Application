package models;

import models.other.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistModel extends EntityModel {

    private boolean is_band;
    private String path_to_image;
    private String genre;
    private List<Interval> intervals;
    private String members; //solo artists do not have any members

    public ArtistModel(int user_id, boolean is_band, String genre) {
        super(user_id);
        this.is_band = is_band;
        this.genre = genre;
        this.path_to_image = "";
        this.members = "";
        this.intervals = new ArrayList<>();
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public boolean isIs_band() {
        return is_band;
    }

    public void setIs_band(boolean is_band) {
        this.is_band = is_band;
    }

    public boolean getIs_band() {
        return is_band;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public String getGenre() {
        return genre;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public String getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistModel that = (ArtistModel) o;
        return is_band == that.is_band &&
                Objects.equals(path_to_image, that.path_to_image) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(intervals, that.intervals) &&
                Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(is_band, path_to_image, genre, intervals, members);
    }
}
