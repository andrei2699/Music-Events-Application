package services.implementation;

import models.DiscussionModel;
import repository.IRepository;
import services.IDiscussionService;

import java.util.ArrayList;
import java.util.List;

public class DiscussionServiceImpl implements IDiscussionService {
    private final IRepository<DiscussionModel> discussionRepository;

    public DiscussionServiceImpl(IRepository<DiscussionModel> discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    @Override
    public DiscussionModel getDiscussion(int id) {
        List<DiscussionModel> allDiscussion = getAllDiscussions();

        if (allDiscussion == null)
            return null;

        return allDiscussion.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<DiscussionModel> getAllDiscussions() {
        return discussionRepository.getAll();
    }

    @Override
    public DiscussionModel updateDiscussion(DiscussionModel discussionModel) {
        return discussionRepository.update(discussionModel);
    }

    @Override
    public DiscussionModel createDiscussion(DiscussionModel discussionModel) {
        List<DiscussionModel> allDiscussions = getAllDiscussions();

        if (allDiscussions == null)
            return discussionRepository.create(discussionModel);

        for (DiscussionModel discussion : allDiscussions) {
            if (discussion.getId() == discussionModel.getId()) {
                return updateDiscussion(discussionModel);
            }
        }
        return discussionRepository.create(discussionModel);
    }

    @Override
    public List<DiscussionModel> getDiscussionUsingArtistId(int artist_id) {
        List<DiscussionModel> allDiscussions = getAllDiscussions();

        if (allDiscussions == null)
            return new ArrayList<>();

        List<DiscussionModel> searchResults = new ArrayList<>();

        for (DiscussionModel discussion : allDiscussions)
            if (discussion.getArtist_id() == artist_id)
                searchResults.add(discussion);

        return searchResults;
    }

    @Override
    public List<DiscussionModel> getDiscussionsUsingBarId(int bar_manager_id) {
        List<DiscussionModel> allDiscussions = getAllDiscussions();

        if (allDiscussions == null)
            return new ArrayList<>();

        List<DiscussionModel> searchResults = new ArrayList<>();

        for (DiscussionModel discussion : allDiscussions)
            if (discussion.getBar_manager_id() == bar_manager_id)
                searchResults.add(discussion);

        return searchResults;
    }
}
