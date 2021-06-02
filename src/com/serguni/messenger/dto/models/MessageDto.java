package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class MessageDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;

    private long id;
    private Date sendTime;
    private String text;
    private Date readTime;
    private long chatId;
    private String userSenderNickname;
    private Set<MessageDto> redirectedMessages;
    private Set<ContentDto> contents;

//    public MessageDto(Message message) {
//        id = message.getId();
//        sendTime = message.getSendTime();
//        text = message.getText();
//        readTime = message.getReadTime();
//        chat = new ChatDto(message.getChat());
//        user = new UserDto(message.getUser());
//        redirectedMessages = new HashSet<>();
//        for (Message redirectedMessage : message.getRedirectedMessages()){
//            redirectedMessages.add(new MessageDto(redirectedMessage));
//        }
//        contents = new HashSet<>();
//        for (Content content : message.getContents()) {
//            contents.add(new ContentDto(content));
//        }
//    }

    public MessageDto(String text, long chatId, String userSenderNickname) {
        this.text = text;
        this.chatId = chatId;
        this.userSenderNickname = userSenderNickname;
    }


//    public MessageDto(long id,
//                      Date sendTime,
//                      String text,
//                      Date readTime,
//                      UserInfoDto user) {
//        this.id = id;
//        this.sendTime = sendTime;
//        this.text = text;
//        this.readTime = readTime;
//        this.user = user;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getUserSenderNickname() {
        return userSenderNickname;
    }

    public void setUserSenderNickname(String userSenderNickname) {
        this.userSenderNickname = userSenderNickname;
    }

    public Set<MessageDto> getRedirectedMessages() {
        return redirectedMessages;
    }

    public void setRedirectedMessages(Set<MessageDto> messages) {
        this.redirectedMessages = messages;
    }

    public Set<ContentDto> getContents() {
        return contents;
    }

    public void setContents(Set<ContentDto> contents) {
        this.contents = contents;
    }
}

