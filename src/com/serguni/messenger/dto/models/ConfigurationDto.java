package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;

public class ConfigurationDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;

    private long userId;
    private UserDto user;
    private boolean msgFromFriendsOnly;
    private boolean showLastOnline;
    private boolean invisible;

    public ConfigurationDto(long userId, boolean msgFromFriendsOnly, boolean showLastOnline, boolean invisible) {
        this.userId = userId;
        this.msgFromFriendsOnly = msgFromFriendsOnly;
        this.showLastOnline = showLastOnline;
        this.invisible = invisible;
    }

    //    public ConfigurationDto(Configuration configuration) {
//        this.userId = configuration.getUserId();
//        this.msgFromFriendsOnly = configuration.isMsgFromFriendsOnly();
//        this.invisible = configuration.isInvisible();
//        this.showLastOnline = configuration.isShowLastOnline();
//    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public boolean isMsgFromFriendsOnly() {
        return msgFromFriendsOnly;
    }

    public void setMsgFromFriendsOnly(boolean msgFromFriendsOnly) {
        this.msgFromFriendsOnly = msgFromFriendsOnly;
    }

    public boolean isShowLastOnline() {
        return showLastOnline;
    }

    public void setShowLastOnline(boolean showLastOnline) {
        this.showLastOnline = showLastOnline;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    @Override
    public String toString() {
        return "ConfigurationDto{" +
                "userId=" + userId +
                ", user=" + user +
                ", msgFromFriendsOnly=" + msgFromFriendsOnly +
                ", showLastOnline=" + showLastOnline +
                ", invisible=" + invisible +
                '}';
    }
}
