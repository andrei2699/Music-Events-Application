package models;

import models.other.Interval;

import java.util.ArrayList;
import java.util.List;

public class ArtistModel {

    private final int user_id;
    private boolean is_band;
    private String path_to_image;
    private String genre;
    private List<Interval> intervals;
    private String members; //solo artists do not have any members

    public ArtistModel(int user_id, boolean is_band, String genre) {
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
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
}
