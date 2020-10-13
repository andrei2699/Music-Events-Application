package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.other.Interval;
import models.other.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscussionModel extends EntityModel {
    private final List<Integer> ids;
    private String messages;

    public DiscussionModel(int id, int bar_manager_id, int artist_id) {
        super(id);
        ids = new ArrayList<>();
        ids.add(bar_manager_id);
        ids.add(artist_id);
        messages = "";
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<Message> getMessages() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(messages, new TypeToken<ArrayList<Message>>(){}.getType());
    }

    public void setMessages(List<Message> messages) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        this.messages = gson.toJson(messages);
    }

    public void addMessage(Message m) {
        List<Message> messages = getMessages();
        messages.add(m);
        setMessages(messages);
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
        List<Message> messages = getMessages();
        if (messages == null || messages.size() == 0)
            return false;
        return !messages.get(messages.size() - 1).isSeen() && messages.get(messages.size() - 1).getSender_id() != user_id;
    }
}
