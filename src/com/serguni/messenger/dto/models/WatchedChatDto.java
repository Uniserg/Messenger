package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class WatchedChatDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    public static class WatchedChatPK implements Serializable {
        protected long chatId;
        protected long userId;

        public WatchedChatPK() {
        }

        public WatchedChatPK(long chatId, long userId) {
            this.chatId = chatId;
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WatchedChatPK that = (WatchedChatPK) o;
            return chatId == that.chatId && userId == that.userId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(chatId, userId);
        }
    }

    private long chatId;
    private long userId;
    private String name;
    private Date syncTime;
    private boolean isAdmin;
    private boolean isBlocked;
    private ChatDto chat;

    public WatchedChatDto(long chatId,
                          long userId,
                          String name,
                          Date syncTime,
                          boolean isAdmin,
                          boolean isBlocked,
                          ChatDto chat) {
        this.chatId = chatId;
        this.userId = userId;
        this.name = name;
        this.syncTime = syncTime;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
        this.chat = chat;
    }

    //    public WatchedChatDto(WatchedChat watchedChat) {
//        chatId = watchedChat.getChatId();
//        userId = watchedChat.getUserId();
//        name = watchedChat.getName();
//        syncTime = watchedChat.getSyncTime();
//        isAdmin = watchedChat.isAdmin();
//        isBlocked = watchedChat.isBlocked();
//        chat = new ChatDto(watchedChat.getChat());
//    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public ChatDto getChat() {
        return chat;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }
}
