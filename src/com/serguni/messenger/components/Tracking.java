package com.serguni.messenger.components;


import java.util.Date;

public interface Tracking {
    default void updateUsername(String newLastName, String newFirstName) {}

    default void updateAboutMe(String newAboutMe) {};

    default void updateAvatar(byte[] newAvatar) {};

    default void updateLastOnline(Date newLastOnline) {};
}
