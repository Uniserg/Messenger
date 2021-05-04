package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class UserDto extends UserInfoDto {
    @Serial
    private static final long serialVersionUID = 1;

    private Set<SessionDto> sessions;
    private Set<UserInfoDto> friends;
    private ConfigurationDto configuration;
//    private Set<WatchedChatDto> watchedChats;
    private Map<UserInfoDto, WatchedChatDto> watchedPrivateChats;
    private Map<GroupDto, WatchedChatDto> watchedGroupChats;
//    private List<TemporaryKeyDto> temporaryKeys;
    private Set<BlockedUserDto> blockedUsers;
    private Set<FriendRequestDto> incomingFriendRequests;
    private Set<FriendRequestDto> outgoingFriendRequests;



    public UserDto(long id,
                   String nickname,
                   String email,
                   String firstName,
                   String lastName,
                   String aboutMe,
                   byte[] avatar,
                   Date lastOnline) {
        super(id, nickname, email, firstName, lastName, aboutMe, avatar, lastOnline);
    }

    public UserDto(String nickname, String email, String firstName, String lastName, String aboutMe) {
        super(nickname, email, firstName, lastName, aboutMe);
    }

    //    public UserDto(User user) {
//        id = user.getId();
//        nickname = user.getNickname();
//        email = user.getEmail();
//        firstName = user.getFirstName();
//        lastName = user.getLastName();
//        aboutMe = user.getAboutMe();
//        avatar = user.getAvatar();
//        sessions = new ArrayList<>();
//        for (Session session : user.getSessions()) {
//            sessions.add(new SessionDto(session));
//        }
//        friends = new HashSet<>();
//        for (User friend : user.getFriends()){
//            friends.add(new UserDto(friend));
//        }
//        configuration = new ConfigurationDto(user.getConfiguration());
//        watchedChats = new HashSet<>();
//        for (WatchedChat watchedChat : user.getWatchedChats()) {
//            watchedChats.add(new WatchedChatDto(watchedChat));
//        }
//        temporaryKeys = new ArrayList<>();
//        for (TemporaryKey temporaryKey : user.getTemporaryKeys()) {
//            temporaryKeys.add(new TemporaryKeyDto(temporaryKey));
//        }
//        blockedUsers = new HashSet<>();
//        for (BlockedUser blockedUser : user.getBlockedUsers()) {
//            blockedUsers.add(new BlockedUserDto(blockedUser));
//        }
//        incomingFriendRequests = new ArrayList<>();
//        for (FriendRequest friendRequest : user.getIncomingFriendRequests()) {
//            incomingFriendRequests.add(new FriendRequestDto(friendRequest));
//        }
//        outgoingFriendRequests = new ArrayList<>();
//        for (FriendRequest friendRequest : user.getOutgoingFriendRequests()) {
//            outgoingFriendRequests.add(new FriendRequestDto(friendRequest));
//        }
//
//    }

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

    public Map<UserInfoDto, WatchedChatDto> getWatchedPrivateChats() {
        return watchedPrivateChats;
    }

    public void setWatchedPrivateChats(Map<UserInfoDto, WatchedChatDto> watchedPrivateChats) {
        this.watchedPrivateChats = watchedPrivateChats;
    }

    public Map<GroupDto, WatchedChatDto> getWatchedGroupChats() {
        return watchedGroupChats;
    }

    public void setWatchedGroupChats(Map<GroupDto, WatchedChatDto> watchedGroupChats) {
        this.watchedGroupChats = watchedGroupChats;
    }

    //    public List<TemporaryKeyDto> getTemporaryKeys() {
//        return temporaryKeys;
//    }
//
//    public void setTemporaryKeys(List<TemporaryKeyDto> temporaryKeys) {
//        this.temporaryKeys = temporaryKeys;
//    }

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
                ", watchedPrivateChats=" + watchedPrivateChats +
                ", watchedGroupChats=" + watchedGroupChats +
                ", blockedUsers=" + blockedUsers +
                ", incomingFriendRequests=" + incomingFriendRequests +
                ", outgoingFriendRequests=" + outgoingFriendRequests +
                '}';
    }
}