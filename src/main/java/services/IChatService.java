package services;

import models.ChatModel;

import java.util.List;

public interface IChatService {
    ChatModel getChat(int chat_id);

    List<ChatModel> getAllChats();

    ChatModel updateChat(ChatModel chatModel);

    ChatModel createChat(ChatModel chatModel);

    List<ChatModel> getChatsUsingArtistId(int artist_id);

    List<ChatModel> getChatsUsingBarId(int bar_manager_id);
}
