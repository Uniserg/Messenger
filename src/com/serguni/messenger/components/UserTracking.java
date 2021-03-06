package com.serguni.messenger.components;

import java.util.Date;

public interface UserTracking {
    void updateNickname(String newNickname);
    void updateFullName(String lastName, String firstName);
    void updateAboutMe(String newAboutMe);
    void updateAvatar(byte[] newAvatar);
    void updateLastOnline(Date newLastOnline);
}
