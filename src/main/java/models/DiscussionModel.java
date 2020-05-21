package models;

import models.other.Message;

import java.util.ArrayList;
import java.util.List;

public class DiscussionModel extends EntityModel {
    private int bar_manager_id;
    private int artist_id;
    private List<Message> messages;

    public DiscussionModel(int id, int bar_manager_id, int artist_id) {
        super(id);
        this.bar_manager_id = bar_manager_id;
        this.artist_id = artist_id;
        messages = new ArrayList<>();
    }

    public int getBar_manager_id() {
        return bar_manager_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message m) {
        messages.add(m);
    }
}
