package com.serguni.messenger.dto;


import com.serguni.messenger.dto.models.UserDto;

import java.io.Serializable;

public class Configuration implements Serializable {


    private long userId;
    private UserDto user;
    private boolean msgFromFriendsOnly;
    private boolean showLastOnline;
    private boolean invisible;

    public Configuration(UserDto user) {
        this.user = user;
    }

    public Configuration() {
    }

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
}
