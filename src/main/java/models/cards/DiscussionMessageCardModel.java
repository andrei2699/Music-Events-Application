package models.cards;

import models.other.Message;

public class DiscussionMessageCardModel implements TableCardModel {
    private final Message message;
    private final boolean isSender;

    public DiscussionMessageCardModel(Message message, boolean isSender) {
        this.message = message;
        this.isSender = isSender;
    }

    public Message getMessage() {
        return message;
    }

    public boolean isSender() {
        return isSender;
    }

    public boolean containsFilter(String filter) {
        return true;
    }

    public DiscussionMessageCardModel getDiscussionMessageCardModel() {
        return this;
    }
}
