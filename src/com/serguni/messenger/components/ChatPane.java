package com.serguni.messenger.components;

import com.serguni.messenger.controllers.UserChatMenuController;
import com.serguni.messenger.dto.models.MessageDto;
import com.serguni.messenger.dto.models.UserInfoDto;
import com.serguni.messenger.dto.models.WatchedChatDto;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class ChatPane extends UserTrackingImpl {
    private final AnchorPane anchorPane;
    private final Label messageLabel;
    private final Label dateLabel;
    private final Label author;

    public ChatPane(WatchedChatDto watchedChatDto, UserInfoDto otherUser, UserChatMenuController userChatMenuController) {
        super(otherUser);

        System.out.println(watchedChatDto.getMessages());

        MessageDto messageDto = null;
        if (!watchedChatDto.getMessages().isEmpty())
            messageDto = watchedChatDto.getMessages().last();

        anchorPane = new AnchorPane();

        messageLabel = new Label();
        dateLabel = new Label();
        author = new Label();


        if (messageDto != null) {

            MessagePane.setMessageAuthor(messageDto.getUserSenderNickname(), author);
            messageLabel.setText(messageDto.getText());
            dateLabel.setText(messageDto.getSendTime().toString());
        }
        author.setLayoutX(90);
        author.setLayoutY(70);
        messageLabel.setLayoutX(130);
        messageLabel.setLayoutY(50);
        dateLabel.setLayoutX(220);
        dateLabel.setLayoutY(70);


        avatar.setRadius(30);
        avatar.setCenterX(45);
        avatar.setCenterY(50);

        fullName.setLayoutX(90);
        fullName.setLayoutY(20);


        anchorPane.setMinHeight(100);
        anchorPane.getChildren().addAll(avatar, fullName, messageLabel, dateLabel, author);

        anchorPane.setOnMouseClicked(mouseEvent -> {
            UserChatMenuController.setSelectedUser(otherUser.getId());
            userChatMenuController.handleShowChatArea();
        });
        anchorPane.setOnMouseEntered(e -> anchorPane.setStyle("-fx-background-color: #3a3a3a"));
        anchorPane.setOnMouseExited(e -> anchorPane.setStyle("-fx-background-color: #1d1d1d"));


    }

//    private static UserInfoDto getOtherUser(WatchedChatDto watchedChatDto, long userId) {
//        UserInfoDto userInfoDto = watchedChatDto.getUsers().last();
//        if (userInfoDto.getId() == userId)
//            userInfoDto = watchedChatDto.getUsers().first();
//
//        return userInfoDto;
//    }

    public AnchorPane getChatPane() {
        return anchorPane;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public Label getAuthor() {
        return author;
    }
}
