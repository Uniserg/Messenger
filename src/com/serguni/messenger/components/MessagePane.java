package com.serguni.messenger.components;

import com.serguni.messenger.Main;
import com.serguni.messenger.dto.models.MessageDto;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class MessagePane {
    private final AnchorPane anchorPane;
    private final Label sendTime;
    private final Label text;
    private final Label author;

    public MessagePane(MessageDto messageDto) {
        anchorPane = new AnchorPane();
        sendTime = new Label();
        text = new Label();
        author = new Label();

        AnchorPane.setRightAnchor(sendTime, 20.0);;
        sendTime.setText(messageDto.getSendTime().toString());

        text.setLayoutY(30);
        AnchorPane.setLeftAnchor(text, 50.0);
        text.setMaxWidth(400);
        text.setWrapText(true);
        text.setText(messageDto.getText());

        setMessageAuthor(messageDto.getUserSenderNickname(), author);
        AnchorPane.setLeftAnchor(author,20.0);

        anchorPane.setStyle("-fx-background-color: #1d1d1d");
        anchorPane.getChildren().addAll(sendTime, text, author);
    }

    public static void setMessageAuthor(String nickname, Label label) {
        if (Main.user.getNickname().equals(nickname)) {
            nickname = "Me:";
            label.setStyle("-fx-text-fill: #ee686f");
        } else {
            label.setStyle("-fx-text-fill: #6541cf");
        }

        label.setText(nickname);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public Label getSendTime() {
        return sendTime;
    }

    public Label getText() {
        return text;
    }

    public Label getAuthor() {
        return author;
    }
}
