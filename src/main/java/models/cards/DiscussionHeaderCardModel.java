package models.cards;

import main.LoggedUserData;
import models.DiscussionModel;
import services.ServiceProvider;

import java.util.List;

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

    public String toString() {
        List<Integer> ids = discussionModel.getIds();

        if (ids == null)
            return "";

        Integer currentUserId = LoggedUserData.getInstance().getUserModel().getId();

        if (currentUserId == ids.get(0)) {
            return ServiceProvider.getUserService().getUser(ids.get(1)).getName();
        } else {
            return ServiceProvider.getUserService().getUser(ids.get(0)).getName();
        }
    }
}
