package com.serguni.messenger.dto.models;

import java.util.Date;
import java.util.Set;

public class UserDtoJson {
    private long id;
    private String nickname;
    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Date lastOnline;
    private String avatar;

    private Set<SessionDto> sessions;
    private Set<UserInfoDto> friends;
    private ConfigurationDto configuration;
    private Set<WatchedChatDto> watchedChats;
    private Set<BlockedUserDto> blockedUsers;
    private Set<FriendRequestDto> incomingFriendRequests;
    private Set<FriendRequestDto> outgoingFriendRequests;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<SessionDto> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionDto> sessions) {
        this.sessions = sessions;
    }

    public Set<UserInfoDto> getFriends() {
        return friends;
    }

    public void setFriends(Set<UserInfoDto> friends) {
        this.friends = friends;
    }

    public ConfigurationDto getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationDto configuration) {
        this.configuration = configuration;
    }

    public Set<WatchedChatDto> getWatchedChats() {
        return watchedChats;
    }

    public void setWatchedChats(Set<WatchedChatDto> watchedChats) {
        this.watchedChats = watchedChats;
    }

    public Set<BlockedUserDto> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<BlockedUserDto> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public Set<FriendRequestDto> getIncomingFriendRequests() {
        return incomingFriendRequests;
    }

    public void setIncomingFriendRequests(Set<FriendRequestDto> incomingFriendRequests) {
        this.incomingFriendRequests = incomingFriendRequests;
    }

    public Set<FriendRequestDto> getOutgoingFriendRequests() {
        return outgoingFriendRequests;
    }

    public void setOutgoingFriendRequests(Set<FriendRequestDto> outgoingFriendRequests) {
        this.outgoingFriendRequests = outgoingFriendRequests;
    }
}
