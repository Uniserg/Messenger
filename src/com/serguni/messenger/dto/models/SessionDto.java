package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SessionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private Long id;
    private Date signInTime;
    private Date lastOnline;
    private String ip;
    private String device;
    private String os;
    private String location;
    private boolean isActive;
    private String cookie;

//    public SessionDto(Session session) {
//        id = session.getId();
//        signInTime = session.getSignInTime();
//        lastOnline = session.getLastOnline();
//        ip = session.getIp();
//        device = session.getDevice();
//        os = session.getOs();
//        location = session.getLocation();
//        user = new UserDto(session.getUser());
//        isActive = session.isActive();
//        cookie = session.getCookie();
//    }


    public SessionDto(String ip, String device, String os, String location) {
        this.ip = ip;
        this.device = device;
        this.os = os;
        this.location = location;
    }

    public SessionDto(Long id, Date signInTime, Date lastOnline, String ip, String device, String os, String location, boolean isActive, String cookie) {
        this.id = id;
        this.signInTime = signInTime;
        this.lastOnline = lastOnline;
        this.ip = ip;
        this.device = device;
        this.os = os;
        this.location = location;
        this.isActive = isActive;
        this.cookie = cookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDto session = (SessionDto) o;
        return isActive == session.isActive && id.equals(session.id) && signInTime.equals(session.signInTime) && lastOnline.equals(session.lastOnline) && ip.equals(session.ip) && device.equals(session.device) && os.equals(session.os) && Objects.equals(location, session.location) && cookie.equals(session.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, signInTime, lastOnline, ip, device, os, location, isActive, cookie);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", signInTime=" + signInTime +
                ", lastOnline=" + lastOnline +
                ", ip='" + ip + '\'' +
                ", device='" + device + '\'' +
                ", os='" + os + '\'' +
                ", location='" + location + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
