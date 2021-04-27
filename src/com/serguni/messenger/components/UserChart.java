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
        Platform.runLater(() -> {
            anchorPane.setOnMouseClicked(mouseEvent -> {
                userChatMenuController.showFoundUserInfoMenu(userDto);
            });
        });

        this.userInfo = userDto;

        anchorPane = new AnchorPane();
        icon = new Circle();
        fullName = new Label();
        aboutMe = new Label();
        lastOnline = new Label();

        icon.setRadius(30);
        icon.setCenterX(45);
        icon.setCenterY(50);
        changeAvatar();

        fullName.setText(userDto.getLastName() + " " + userDto.getFirstName());
        fullName.setLayoutX(90);
        fullName.setLayoutY(20);
        if (fullName.getText().equals(" ")) {
            fullName.setText(userDto.getNickname());
        }

        aboutMe.setLayoutX(90);
        aboutMe.setLayoutY(60);
        aboutMe.setText(userDto.getAboutMe());

        lastOnline.setLayoutX(120);
        lastOnline.setLayoutY(40);
        Platform.runLater(this::setLastOnline);



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

        UserInfoDto userInfoDto = (UserInfoDto) userInfoObject;

        Platform.runLater(() ->{
            System.out.println("МЫ ЗДЕСЬ = " + userInfoDto.getLastName());
            fullName.setText(userInfoDto.getLastName() + " " + userInfoDto.getFirstName());
            aboutMe.setText(userInfoDto.getAboutMe());
            userInfo.setLastOnline(userInfoDto.getLastOnline());
            setLastOnline();

            if (!Arrays.equals(userInfoDto.getAvatar(), userInfo.getAvatar())) {
                userInfo.setAvatar(userInfoDto.getAvatar());
                changeAvatar();
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
