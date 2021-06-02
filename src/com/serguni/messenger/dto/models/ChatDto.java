package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public class ChatDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;

    private long id;
    private String secretKey;
    private Set<MessageDto> messages;
    private Set<WatchedChatDto> watchedChats;

//    public ChatDto(Chat chat) {
//        id = chat.getId();
//        secretKey = chat.getSecretKey();
//
//        messages = new HashSet<>();
//        for (Message message : chat.getMessages()) {
//            messages.add(new MessageDto(message));
//        }
//
//        watchedChats = new HashSet<>();
//        for (WatchedChat watchedChat : chat.getWatchedChats()) {
//            watchedChats.add(new WatchedChatDto(watchedChat));
//        }
//    }


    public ChatDto(long id,
                   String secretKey) {
        this.id = id;
        this.secretKey = secretKey;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Set<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageDto> messages) {
        this.messages = messages;
    }

    public Set<WatchedChatDto> getWatchedChats() {
        return watchedChats;
    }

    public void setWatchedChats(Set<WatchedChatDto> watchedChats) {
        this.watchedChats = watchedChats;
    }
}

