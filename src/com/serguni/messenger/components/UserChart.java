package com.serguni.messenger.components;

import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;


public class UserChart extends UserTrackingImpl {

    private AnchorPane anchorPane;

    public UserChart(UserInfoDto userInfoDto, UserChatMenuController userChatMenuController) {
        super(userInfoDto);

        anchorPane = new AnchorPane();

        avatar.setRadius(30);
        avatar.setCenterX(45);
        avatar.setCenterY(50);

        fullName.setLayoutX(90);
        fullName.setLayoutY(20);

        aboutMe.setLayoutX(90);
        aboutMe.setLayoutY(60);

        lastOnline.setLayoutX(120);
        lastOnline.setLayoutY(40);

        nickname.setLayoutX(220);
        nickname.setLayoutY(70);

        anchorPane.setMinHeight(100);
        anchorPane.getChildren().addAll(avatar, fullName, aboutMe, lastOnline, nickname);

        Platform.runLater(() -> anchorPane.setOnMouseClicked(mouseEvent -> userChatMenuController.showFoundUserInfoMenu(userInfoDto)));

        anchorPane.setOnMouseEntered(e -> anchorPane.setStyle("-fx-background-color: #3a3a3a"));
        anchorPane.setOnMouseExited(e -> anchorPane.setStyle("-fx-background-color: #1d1d1d"));
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    //    public static void TranslateToUserChart(UserTrackingImpl userInfo, UserChatMenuController userChatMenuController) {
//
//        Circle avatar = userInfo.getAvatar();
//
//        avatar.setRadius(30);
//        avatar.setCenterX(45);
//        avatar.setCenterY(50);
//
//        Label fullName = userInfo.getFullName();
//        fullName.setLayoutX(90);
//        fullName.setLayoutY(20);
//
//        Label aboutMe = userInfo.getAboutMe();
//        aboutMe.setLayoutX(90);
//        aboutMe.setLayoutY(60);
//
//        Label lastOnline = userInfo.getLastOnline();
//        lastOnline.setLayoutX(120);
//        lastOnline.setLayoutY(40);
//
//        Label nickname = userInfo.getNickname();
//        nickname.setLayoutX(220);
//        nickname.setLayoutY(70);
//
//        userInfo.getCurrentWindow().setMinHeight(100);
//
//        Platform.runLater(() -> userInfo.getCurrentWindow().setOnMouseClicked(mouseEvent -> userChatMenuController.showFoundUserInfoMenu(userInfo.getUserInfoDto())));
//
//        userInfo.getCurrentWindow().setOnMouseEntered(e -> userInfo.getCurrentWindow().setStyle("-fx-background-color: #3a3a3a"));
//        userInfo.getCurrentWindow().setOnMouseExited(e -> userInfo.getCurrentWindow().setStyle("-fx-background-color: #1d1d1d"));
//    }
}
