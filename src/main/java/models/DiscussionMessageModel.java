package models;

public class DiscussionMessageModel {
    private final String date;
    private final String text;
    private final boolean isSender;

    public DiscussionMessageModel(String date, String text, boolean isSender) {
        this.date = date;
        this.text = text;
        this.isSender = isSender;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public boolean isSender() {
        return isSender;
    }
}
