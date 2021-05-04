package com.serguni.messenger.components;

import com.serguni.messenger.dto.models.UserInfoDto;
import javafx.scene.layout.AnchorPane;

public class UserPane extends UserTrackingImpl {

    private final AnchorPane anchorPane;

    public UserPane(UserInfoDto userInfoDto) {
        super(userInfoDto);
        anchorPane = new AnchorPane();

        avatar.setRadius(30);
        avatar.setCenterX(105);
        avatar.setCenterY(50);

        fullName.setLayoutX(150);
        fullName.setLayoutY(20);

        aboutMe.setLayoutX(150);
        aboutMe.setLayoutY(60);

        lastOnline.setLayoutX(170);
        lastOnline.setLayoutY(40);

        nickname.setLayoutX(220);
        nickname.setLayoutY(70);
        nickname.setStyle("-fx-text-fill: #6541cf");

        anchorPane.getChildren().addAll(avatar, fullName, aboutMe, lastOnline, nickname);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

}
