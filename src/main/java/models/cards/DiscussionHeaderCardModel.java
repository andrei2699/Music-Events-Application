package models.cards;

import models.DiscussionModel;

public class DiscussionHeaderCardModel implements TableCardModel {
    private final DiscussionModel discussionModel;

    public DiscussionHeaderCardModel(DiscussionModel discussionModel) {
        this.discussionModel = discussionModel;
    }

    public DiscussionModel getDiscussionModel() {
        return discussionModel;
    }

    public boolean containsFilter(String filter) {
        return true;
    }

    public DiscussionHeaderCardModel getDiscussionHeaderCardModel() {
        return this;
    }
}
