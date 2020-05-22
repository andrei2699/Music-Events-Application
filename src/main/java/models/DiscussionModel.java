package models;

import models.other.Message;

import java.util.ArrayList;
import java.util.List;

public class DiscussionModel extends EntityModel {
    private List<Integer> ids;
    private List<Message> messages;

    public DiscussionModel(int id, int bar_manager_id, int artist_id) {
        super(id);
        ids = new ArrayList<>();
        ids.add(bar_manager_id);
        ids.add(artist_id);
        messages = new ArrayList<>();
    }

    public List<Integer> getIds() { return ids; }

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
