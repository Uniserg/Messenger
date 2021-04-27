//package com.serguni.messenger.dto;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.Objects;
//
//public class SessionDto implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 1;
//    private long id;
//    private String device;
//    private String ip;
//    private String os;
//    private String location;
//    private UserDto user;
//
//    public SessionDto(String device, String ip, String os, String location, UserDto user) {
//        this.device = device;
//        this.ip = ip;
//        this.os = os;
//        this.location = location;
//        this.user = user;
//    }
//
//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getDevice() {
//        return device;
//    }
//
//    public void setDevice(String device) {
//        this.device = device;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getOs() {
//        return os;
//    }
//
//    public void setOs(String os) {
//        this.os = os;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public UserDto getUser() {
//        return user;
//    }
//
//    public void setUser(UserDto user) {
//        this.user = user;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SessionDto that = (SessionDto) o;
//        return id == that.id && device.equals(that.device) && ip.equals(that.ip) && os.equals(that.os) && location.equals(that.location) && user.equals(that.user);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, device, ip, os, location, user);
//    }
//
//    @Override
//    public String toString() {
//        return "SessionDto{" +
//                "id=" + id +
//                ", device='" + device + '\'' +
//                ", ip='" + ip + '\'' +
//                ", os='" + os + '\'' +
//                ", location='" + location + '\'' +
//                ", user=" + user +
//                '}';
//    }
//}
