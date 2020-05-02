package models;

import java.util.ArrayList;
import java.util.List;

public class ArtistModel {

    private int user_id;
    private boolean is_band;
    private String path_to_image;
    private String genre;
    private String name; //for a solo artist it is his/her name, for a band it is band's name
    private List<Interval> intervals;
    private List<String> members; //solo artists do not have any members

    public ArtistModel(int user_id, boolean is_band, String genre, String name) {
        this.user_id = user_id;
        this.is_band = is_band;
        this.genre = genre;
        this.name = name;
        this.path_to_image = "";
        this.members=new ArrayList<>();
        this.intervals = new ArrayList<>();
    }

    public ArtistModel(int user_id, boolean is_band, String genre, String name, List<String> members) {
        this.user_id = user_id;
        this.is_band = is_band;
        this.genre = genre;
        this.name = name;
        this.members = members;
        this.path_to_image = "";
        this.intervals = new ArrayList<>();
        this.members=new ArrayList<>();
        this.members.addAll(members);
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    public void setMembers(List<String> members) {
        this.members.clear();
        this.members.addAll(members);
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

    public String getName() {
        return name;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public List<String> getMembers() {
        return members;
    }
}
