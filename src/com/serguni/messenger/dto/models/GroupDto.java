package com.serguni.messenger.dto.models;

public class GroupDto {
    private long id;
    private byte[] icon;
    private String name;
    private String password;

    public GroupDto(long id, byte[] icon, String name, String password) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
