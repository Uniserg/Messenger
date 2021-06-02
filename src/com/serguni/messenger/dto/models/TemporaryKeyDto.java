package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class TemporaryKeyDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;

    private long id;
    private String key;
    private Date createTime;
    private UserDto user;

//    public TemporaryKeyDto(TemporaryKey temporaryKey) {
//        id = temporaryKey.getId();
//        key = temporaryKey.getKey();
//        createTime = temporaryKey.getCreateTime();
//        user = new UserDto(temporaryKey.getUser());
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}