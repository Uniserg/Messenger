package com.serguni.messenger.dto;

//import java.io.Serial;
import java.io.Serializable;


public class SocketMessage implements Serializable {
    public enum MessageType {
        SEARCH_USER,
        LOGOUT,
        LOGIN,
        EDIT_NAME,
        EDIT_ABOUT_ME,
        EDIT_AVATAR,
        EDIT_CONFIGURATION,
        UPDATE_LAST_ONLINE_TO_TRACKING_USERS,
        UPDATE_LAST_ONLINE_TO_OTHER_USER_SESSIONS,
        //        UPDATE_TRACKING_USER,
        ADD_NEW_SESSION,
        DELETE_ALL_OTHER_SESSIONS,
        DELETE_OTHER_SESSION,
        CREATE_NEW_CHAT,
        CHAT_MESSAGE
    }

//    @Serial
    private static final long serialVersionUID = 3;

    private MessageType type;
    private Object body;

    public SocketMessage(MessageType type, Object body) {
        this.type = type;
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
