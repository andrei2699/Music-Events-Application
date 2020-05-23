package models.other;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return sender_id == message.sender_id &&
                seen == message.seen &&
                Objects.equals(date, message.date) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, text, sender_id, seen);
    }
}
