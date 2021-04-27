//package com.serguni.messenger.dto;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.Objects;
//
//public class UserDto implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 1;
//    private Long id;
//    private String nickname;
//    private String email;
//    private String firstName;
//    private String lastName;
//    private String aboutMe;
//    private byte[] avatar;
//
//    public UserDto(Long id,
//                   String nickname,
//                   String email,
//                   String firstName,
//                   String lastName,
//                   String aboutMe,
//                   byte[] avatar) {
//        this.id = id;
//        this.nickname = nickname;
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.aboutMe = aboutMe;
//        this.avatar = avatar;
//    }
//
//    public UserDto(String nickname, String email, String firstName, String lastName, String aboutMe) {
//        this(null, nickname, email, firstName, lastName, aboutMe, null);
//    }
//
//    public UserDto() {
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserDto userDto = (UserDto) o;
//        return id.equals(userDto.id) && nickname.equals(userDto.nickname) && email.equals(userDto.email) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(aboutMe, userDto.aboutMe) && Arrays.equals(avatar, userDto.avatar);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hash(id, nickname, email, firstName, lastName, aboutMe);
//        result = 31 * result + Arrays.hashCode(avatar);
//        return result;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getAboutMe() {
//        return aboutMe;
//    }
//
//    public void setAboutMe(String aboutMe) {
//        this.aboutMe = aboutMe;
//    }
//
//    public byte[] getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(byte[] avatar) {
//        this.avatar = avatar;
//    }
//
//    @Override
//    public String toString() {
//        return "UserDto{" +
//                "id=" + id +
//                ", nickname='" + nickname + '\'' +
//                ", email='" + email + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", aboutMe='" + aboutMe + '\'' +
//                ", avatar=" + Arrays.toString(avatar) +
//                '}';
//    }
//}
