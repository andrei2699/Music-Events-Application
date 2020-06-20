package models.cards;

import main.LoggedUserData;
import models.DiscussionModel;
import services.IUserService;
import services.ServiceProvider;

import java.util.List;

public class DiscussionHeaderCardModel implements TableCardModel {
    private final DiscussionModel discussionModel;
    private IUserService userService;

    public DiscussionHeaderCardModel(DiscussionModel discussionModel, IUserService userService) {
        this.discussionModel = discussionModel;
        this.userService = userService;
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

        int currentUserId = LoggedUserData.getInstance().getUserModel().getId();

        if (currentUserId == ids.get(0)) {
            return userService.getUser(ids.get(1)).getName();
        } else {
            return userService.getUser(ids.get(0)).getName();
        }
    }
}
