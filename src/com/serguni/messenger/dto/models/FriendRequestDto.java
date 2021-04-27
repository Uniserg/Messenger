package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class FriendRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    public static class FriendRequestFK implements Serializable {
        @Serial
        private static final long serialVersionUID = 1;

        protected long fromUserId;
        protected long toUserId;

        public FriendRequestFK() {
        }

        public FriendRequestFK(long fromUserId, long toUserId) {
            this.fromUserId = fromUserId;
            this.toUserId = toUserId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FriendRequestFK that = (FriendRequestFK) o;
            return fromUserId == that.fromUserId && toUserId == that.toUserId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fromUserId, toUserId);
        }
    }

    private long fromUserId;
    private long toUserId;
    private Date sendTime;
    private Date acceptTime;
    private UserInfoDto fromUser;
    private UserInfoDto toUser;

    public FriendRequestDto(long fromUserId,
                            long toUserId,
                            Date sendTime,
                            Date acceptTime,
                            UserInfoDto fromUser,
                            UserInfoDto toUser) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.sendTime = sendTime;
        this.acceptTime = acceptTime;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public UserInfoDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserInfoDto fromUser) {
        this.fromUser = fromUser;
    }

    public UserInfoDto getToUser() {
        return toUser;
    }

    public void setToUser(UserInfoDto toUser) {
        this.toUser = toUser;
    }
}