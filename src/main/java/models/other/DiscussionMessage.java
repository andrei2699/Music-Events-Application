package models.other;

public class DiscussionMessage {
    private final String date;
    private final String text;
    private final int senderId;

    public DiscussionMessage(String date, String text, int senderId) {
        this.date = date;
        this.text = text;
        this.senderId = senderId;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getSenderId() {
        return senderId;
    }
}
