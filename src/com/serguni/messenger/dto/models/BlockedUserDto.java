package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BlockedUserDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;

    public static class BlockedUserPK implements Serializable {
//        @Serial
        private static final long serialVersionUID = 1;

        protected long userId;
        protected long blockedUserId;

        public BlockedUserPK() {
        }

        public BlockedUserPK(long userId, long blockedUserId) {
            this.userId = userId;
            this.blockedUserId = blockedUserId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockedUserPK that = (BlockedUserPK) o;
            return userId == that.userId && blockedUserId == that.blockedUserId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, blockedUserId);
        }
    }


    private long userId;
    private long blockedUserId;
    private UserInfoDto blockedUser;
    private Date blockTime;

//    public BlockedUserDto(BlockedUser blockedUserR) {
//        userId = blockedUserR.getUserId();
//        blockedUserId = blockedUserR.getBlockedUserId();
//        user = new UserDto(blockedUserR.getUser());
//        blockedUser = new UserDto(blockedUserR.getBlockedUser());
//        blockTime = blockedUserR.getBlockTime();
//    }


    public BlockedUserDto(long userId, long blockedUserId, UserInfoDto blockedUser, Date blockTime) {
        this.userId = userId;
        this.blockedUserId = blockedUserId;
        this.blockedUser = blockedUser;
        this.blockTime = blockTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public UserInfoDto getBlockedUser() {
        return blockedUser;
    }

    public void setBlockedUser(UserDto blockedUser) {
        this.blockedUser = blockedUser;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Date blockTime) {
        this.blockTime = blockTime;
    }
}