package com.serguni.messenger.components;

import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Date;

public class UserChart implements Tracking {

    private AnchorPane anchorPane;
    private Circle icon;
    private Label fullName;
    private Label aboutMe;
    private Label lastOnline;
    private UserInfoDto userInfo;

    public UserChart(UserInfoDto userDto, UserChatMenuController userChatMenuController) {
        anchorPane = new AnchorPane();

        this.userInfo = userDto;

        icon = new Circle();
        fullName = new Label();
        aboutMe = new Label();
        lastOnline = new Label();

        icon.setRadius(30);
        icon.setCenterX(45);
        icon.setCenterY(50);
        changeAvatar();

        String name = userDto.getLastName() + " " + userDto.getFirstName();

        System.out.println("ИМЯ " + name);

        fullName.setLayoutX(90);
        fullName.setLayoutY(20);
        changeFullName();

        aboutMe.setLayoutX(90);
        aboutMe.setLayoutY(60);
        aboutMe.setText(userDto.getAboutMe());

        lastOnline.setLayoutX(120);
        lastOnline.setLayoutY(40);
        Platform.runLater(this::setLastOnline);


        Platform.runLater(() -> anchorPane.setOnMouseClicked(mouseEvent -> userChatMenuController.showFoundUserInfoMenu(userDto)));

        anchorPane.setMinHeight(100);
        anchorPane.getChildren().addAll(icon, fullName, aboutMe, lastOnline);

        anchorPane.setOnMouseEntered(e -> anchorPane.setStyle("-fx-background-color: #3a3a3a"));
        anchorPane.setOnMouseExited(e -> anchorPane.setStyle("-fx-background-color: #1d1d1d"));
    }

    private void changeAvatar() {
        if (userInfo.getAvatar() != null) {
            ByteArrayInputStream bin = new ByteArrayInputStream(userInfo.getAvatar());
            icon.setFill(new ImagePattern(new Image(bin)));
        } else {
            icon.setFill(Paint.valueOf("#352482"));
        }
    }

    private void changeFullName() {
        String name = userInfo.getLastName() + " " + userInfo.getFirstName();

        if (name.equals(" ")) {
            fullName.setText(userInfo.getNickname());
        } else {
            fullName.setText(name);
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setLastOnline() {
        if (userInfo.getLastOnline().equals(new Date(0))) {
            lastOnline.setText("Online");
        } else {
            //ФОРМАТИРОВАТЬ ДАТУ!!!!
            lastOnline.setText(userInfo.getLastOnline().toString());
        }
    }

    @Override
    public void updateInfo(Object userInfoObject) {
        userInfo = (UserInfoDto) userInfoObject;

        Platform.runLater(() ->{
            changeFullName();
            aboutMe.setText(userInfo.getAboutMe());
            setLastOnline();

            if (!Arrays.equals(userInfo.getAvatar(), userInfo.getAvatar())) {
                changeAvatar();
            } else {
                System.out.println("UserChart -> 104 -> ОДИНАКОВЫЕ АВАТАРЫ???");
            }
        });

//        System.out.println("МЫ ЗДЕСЬ = " + userInfoDto.getLastName());
//        fullName.setText(userInfoDto.getLastName() + " " + userInfoDto.getFirstName());
//        aboutMe.setText(userInfoDto.getAboutMe());
//
//        userInfo.setLastOnline(userInfoDto.getLastOnline());
//
//        Platform.runLater(this::setLastOnline);
    }
}
