package models.other;

public class Message {
    private final String date;
    private final String text;
    private final int sender_id;
    private boolean seen;

    public Message(String date, String text, int sender_id) {
        this.date = date;
        this.text = text;
        this.sender_id = sender_id;
        this.seen = false;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getSender_id() {
        return sender_id;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
