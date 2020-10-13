package services;

import models.DiscussionModel;
import services.implementation.DiscussionNotCreatedException;
import services.implementation.EventNotCreatedException;

import java.util.List;

public interface IDiscussionService {
    DiscussionModel getDiscussion(int discussion_id);

    List<DiscussionModel> getAllDiscussions();

    DiscussionModel updateDiscussion(DiscussionModel discussionModel);

    void createDiscussion(int bar_manager_id, int artist_id) throws DiscussionNotCreatedException;

    List<DiscussionModel> getDiscussionsUsingId(int id);

    boolean checkNewMessage(int user_id);
}
