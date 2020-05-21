package services.implementation;

import models.ChatModel;
import repository.IRepository;
import services.IChatService;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements IChatService {
    private final IRepository<ChatModel> chatRepository;

    public ChatServiceImpl(IRepository<ChatModel> chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatModel getChat(int id) {
        List<ChatModel> allChats = getAllChats();

        if (allChats == null)
            return null;

        return allChats.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<ChatModel> getAllChats() {
        return chatRepository.getAll();
    }

    @Override
    public List<ChatModel> getChatsUsingArtistId(int artist_id) {
        List<ChatModel> allChats = getAllChats();

        if (allChats == null)
            return new ArrayList<>();

        List<ChatModel> searchResults = new ArrayList<>();

        for (ChatModel chat : allChats)
            if (chat.getArtist_id() == artist_id)
                searchResults.add(chat);

        return searchResults;
    }

    @Override
    public List<ChatModel> getChatsUsingBarId(int bar_manager_id) {
        List<ChatModel> allChats = getAllChats();

        if (allChats == null)
            return new ArrayList<>();

        List<ChatModel> searchResults = new ArrayList<>();

        for (ChatModel chat : allChats)
            if (chat.getBar_manager_id() == bar_manager_id)
                searchResults.add(chat);

        return searchResults;
    }

    @Override
    public ChatModel updateChat(ChatModel chatModel) {
        return chatRepository.update(chatModel);
    }

    @Override
    public ChatModel createChat(ChatModel chatModel) {
        List<ChatModel> allChats = getAllChats();

        if (allChats == null)
            return chatRepository.create(chatModel);

        for (ChatModel chat : allChats) {
            if (chat.getId() == chatModel.getId()) {
                return updateChat(chatModel);
            }
        }
        return chatRepository.create(chatModel);
    }
}
