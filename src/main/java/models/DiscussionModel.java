package models;

import models.other.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscussionModel extends EntityModel {
    private final List<Integer> ids;
    private List<Message> messages;

    public DiscussionModel(int id, int bar_manager_id, int artist_id) {
        super(id);
        ids = new ArrayList<>();
        ids.add(bar_manager_id);
        ids.add(artist_id);
        messages = new ArrayList<>();
    }

    public List<Integer> getIds() {
        return ids;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscussionModel that = (DiscussionModel) o;
        return Objects.equals(ids, that.ids) &&
                Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ids, messages);
    }

    public boolean hasUnreadMessagesWith(int user_id) {
        if (messages == null || messages.size() == 0)
            return false;
        return !messages.get(messages.size() - 1).isSeen() && messages.get(messages.size() - 1).getSender_id() != user_id;
    }
}
