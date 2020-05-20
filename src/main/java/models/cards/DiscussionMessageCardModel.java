package models.cards;

import models.DiscussionMessageModel;

public class DiscussionMessageCardModel implements TableCardModel {
    private final DiscussionMessageModel discussionMessageModel;

    public DiscussionMessageCardModel(DiscussionMessageModel discussionModel) {
        this.discussionMessageModel = discussionModel;
    }

    public DiscussionMessageModel getDiscussionMessageModel() {
        return discussionMessageModel;
    }

    public boolean containsFilter(String filter) {
        return true;
    }

    public DiscussionMessageCardModel getDiscussionMessageCardModel() {
        return this;
    }
}
