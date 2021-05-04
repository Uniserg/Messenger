package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class UserInfoDto implements Serializable, Comparable<UserInfoDto> {
    @Serial
    private static final long serialVersionUID = 1;

    private long id;
    private String nickname;
    private String email;
    private String firstName;
    private String lastName;
    private String aboutMe;
    private Date lastOnline;
    private byte[] avatar;

    public UserInfoDto(long id,
                       String nickname,
                       String email,
                       String firstName,
                       String lastName,
                       String aboutMe,
                       byte[] avatar,
                       Date lastOnline) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
        this.avatar = avatar;
        this.lastOnline = lastOnline;
    }

    public UserInfoDto(String nickname,
                       String email,
                       String firstName,
                       String lastName,
                       String aboutMe) {
        this.nickname = nickname;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutMe = aboutMe;
    }

//    public UserInfoDto(long id,
//                       String nickname,
//                       String email,
//                       String firstName,
//                       String lastName,
//                       String aboutMe,
//                       byte[] avatar) {
//        this.id = id;
//        this.nickname = nickname;
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.aboutMe = aboutMe;
//        this.avatar = avatar;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDto userInfoDto = (UserInfoDto) o;
        return id == userInfoDto.id && nickname.equals(userInfoDto.nickname) &&
                email.equals(userInfoDto.email) &&
                Objects.equals(firstName, userInfoDto.firstName) &&
                Objects.equals(lastName, userInfoDto.lastName) &&
                Objects.equals(aboutMe, userInfoDto.aboutMe) &&
                Arrays.equals(avatar, userInfoDto.avatar);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nickname, email, firstName, lastName, aboutMe);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

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

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", lastOnline=" + lastOnline +
                '}';
    }

    @Override
    public int compareTo(UserInfoDto o) {
        return nickname.compareTo(o.getNickname());
    }
}
