package com.serguni.messenger.components;

import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.util.Date;

public abstract class UserTrackingImpl implements UserTracking {
//    private Pane prevWindow;
//    private Pane currentWindow;
    protected Label nickname;
    protected Label fullName;
    protected Circle avatar;
    protected Label lastOnline;
    protected Label aboutMe;
    protected final UserInfoDto userInfoDto;

    public UserTrackingImpl(UserInfoDto userInfoDto) {


//        currentWindow = new AnchorPane();
//        prevWindow = null;

        this.userInfoDto = userInfoDto;

        nickname = new Label();
        fullName = new Label();
        avatar = new Circle();
        lastOnline = new Label();
        aboutMe = new Label();

        updateNickname(userInfoDto.getNickname());
        updateFullName(userInfoDto.getLastName() + " " + userInfoDto.getFirstName());
        updateAvatar(userInfoDto.getAvatar());
        updateAboutMe(userInfoDto.getAboutMe());
        updateLastOnline(userInfoDto.getLastOnline());

//        setWindow(currentWindow);
    }

    @Override
    public void updateNickname(String newNickname) {
        nickname.setText(newNickname);
    }

    @Override
    public void updateFullName(String newFullName) {
        if (newFullName.equals(" ")) {
            fullName.setText(nickname.getText());
        } else {
            fullName.setText(newFullName);
        }
    }

    @Override
    public void updateAboutMe(String newAboutMe) {
        aboutMe.setText(newAboutMe);
    }

    @Override
    public void updateAvatar(byte[] newAvatar) {
        if (newAvatar != null) {
            ByteArrayInputStream bin = new ByteArrayInputStream(newAvatar);
            avatar.setFill(new ImagePattern(new Image(bin)));
        } else {
            avatar.setFill(Paint.valueOf("#352482"));
        }
    }

    @Override
    public void updateLastOnline(Date newLastOnline) {
        if (newLastOnline.equals(new Date(0))) {
            lastOnline.setText("Online");
        } else {
            lastOnline.setText(newLastOnline.toString());
        }
    }

    public UserInfoDto getUserInfoDto() {
        return userInfoDto;
    }

    public Label getFullName() {
        return fullName;
    }

    public void setFullName(Label fullName) {
        this.fullName = fullName;
    }

    public Label getNickname() {
        return nickname;
    }

    public void setNickname(Label nickname) {
        this.nickname = nickname;
    }

    public Circle getAvatar() {
        return avatar;
    }

    public void setAvatar(Circle avatar) {
        this.avatar = avatar;
    }

    public Label getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Label lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Label getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(Label aboutMe) {
        this.aboutMe = aboutMe;
    }

//    public Pane getCurrentWindow() {
//        return currentWindow;
//    }
//
//    private void setWindow(Pane pane) {
//        pane.getChildren().addAll(
//                nickname,
//                fullName,
//                avatar,
//                lastOnline,
//                aboutMe);
//    }
//
//    public void setPrevWindow() {
//        currentWindow = prevWindow;
//        prevWindow = null;
//        setWindow(currentWindow);
//    }
//
//    public void setCurrentWindow(Pane currentWindow) {
//        this.prevWindow = this.currentWindow;
//        this.currentWindow = currentWindow;
//        setWindow(currentWindow);
//    }
}
