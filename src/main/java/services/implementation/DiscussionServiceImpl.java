package services.implementation;

import models.DiscussionModel;
import models.other.Message;
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
    public DiscussionModel createDiscussion(int bar_manager_id, int artist_id) {
        List<DiscussionModel> allDiscussions = getAllDiscussions();

        if (allDiscussions == null) {
            DiscussionModel discussionModel = new DiscussionModel(0, bar_manager_id, artist_id);
            return discussionRepository.create(discussionModel);
        }

        int biggestId = -1;
        for (DiscussionModel discussion : allDiscussions) {
            if (discussion.getId() > biggestId) {
                biggestId = discussion.getId();
            }
            if (discussion.getIds().contains(bar_manager_id) && discussion.getIds().contains(artist_id)) {
                return discussion;
            }
        }

        DiscussionModel discussionModel = new DiscussionModel(biggestId + 1, bar_manager_id, artist_id);

        return discussionRepository.create(discussionModel);
    }

    @Override
    public List<DiscussionModel> getDiscussionsUsingId(int user_id) {
        List<DiscussionModel> allDiscussions = getAllDiscussions();

        if (allDiscussions == null)
            return new ArrayList<>();

        List<DiscussionModel> searchResults = new ArrayList<>();

        for (DiscussionModel discussion : allDiscussions) {
            List<Integer> ids = discussion.getIds();
            if(ids!=null) {
                for (Integer id : ids) {
                    if (user_id == id)
                        searchResults.add(discussion);
                }
            }
        }
        return searchResults;
    }

    public boolean checkNewMessage(int user_id) {
        List<DiscussionModel> discussions = getDiscussionsUsingId(user_id);

        if (discussions == null)
            return false;

        for (DiscussionModel discussion : discussions) {
            List<Message> messages = discussion.getMessages();
            if (messages == null || messages.size() == 0)
                return false;
            if (!messages.get(messages.size() - 1).isSeen() && messages.get(messages.size() - 1).getSender_id() != user_id)
                return true;
        }
        return false;
    }
}
