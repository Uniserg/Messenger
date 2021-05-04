package com.serguni.messenger.components;

import java.util.Date;

public interface UserTracking {
    void updateNickname(String newNickname);
    void updateFullName(String newFullName);
//    void updateLastName(String newLastName);
//    void updateFirstName(String newFirstName);
    void updateAboutMe(String newAboutMe);
    void updateAvatar(byte[] newAvatar);
    void updateLastOnline(Date newLastOnline);
}
