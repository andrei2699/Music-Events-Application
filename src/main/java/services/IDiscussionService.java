package services;

import models.DiscussionModel;

import java.util.List;

public interface IDiscussionService {
    DiscussionModel getDiscussion(int discussion_id);

    List<DiscussionModel> getAllDiscussions();

    DiscussionModel updateDiscussion(DiscussionModel discussionModel);

    DiscussionModel createDiscussion(DiscussionModel discussionModel);

    List<DiscussionModel> getDiscussionUsingArtistId(int artist_id);

    List<DiscussionModel> getDiscussionsUsingBarId(int bar_manager_id);
}
