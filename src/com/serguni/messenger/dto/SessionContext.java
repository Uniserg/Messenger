package com.serguni.messenger.dto;

import com.serguni.messenger.dto.models.SessionDto;

import java.io.Serializable;

public class SessionContext implements Serializable {
    long userId;
    String key;
    SessionDto session;

    public SessionContext(long userId, String key, SessionDto session) {
        this.userId = userId;
        this.key = key;
        this.session = session;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }
}
