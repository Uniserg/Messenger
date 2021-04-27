package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.util.Set;

public class UserDto extends UserInfoDto {
    @Serial
    private static final long serialVersionUID = 1;

    private Set<SessionDto> sessions;
    private Set<UserInfoDto> friends;
    private ConfigurationDto configuration;
    private Set<WatchedChatDto> watchedChats;
    private Set<BlockedUserDto> blockedUsers;
    private Set<FriendRequestDto> incomingFriendRequests;
    private Set<FriendRequestDto> outgoingFriendRequests;



    public UserDto(long id,
                   String nickname,
                   String email,
                   String firstName,
                   String lastName,
                   String aboutMe,
                   byte[] avatar) {
        super(id, nickname, email, firstName, lastName, aboutMe, avatar);
    }

    public UserDto(long id,
                   String nickname,
                   String email,
                   String firstName,
                   String lastName,
                   String aboutMe) {
        this(id, nickname, email, firstName, lastName, aboutMe, null);
    }

    public UserDto(String nickname, String email, String firstName, String lastName, String aboutMe) {
        super(nickname, email, firstName, lastName, aboutMe);
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

    @Override
    public String toString() {
        return "UserDto{" +
                "sessions=" + sessions +
                ", friends=" + friends +
                ", configuration=" + configuration +
                ", watchedChats=" + watchedChats +
                ", blockedUsers=" + blockedUsers +
                ", incomingFriendRequests=" + incomingFriendRequests +
                ", outgoingFriendRequests=" + outgoingFriendRequests +
                '}';
    }
}