package com.serguni.messenger.components;

import com.serguni.messenger.dto.models.MessageDto;
import com.serguni.messenger.dto.models.WatchedChatDto;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ChatArea {

    private final WatchedChatDto watchedChatDto;
    private final VBox vBox;
    private final Map<Long, MessagePane> messages;

    public ChatArea(WatchedChatDto watchedChatDto) {
        this.watchedChatDto = watchedChatDto;

        vBox = new VBox();
        vBox.setSpacing(2);
        vBox.setStyle("-fx-background-color: #1d1d1d");
        messages = new HashMap<>();

        for (MessageDto messageDto : watchedChatDto.getMessages()) {
            MessagePane messagePane = new MessagePane(messageDto);
            messages.put(messageDto.getId(), messagePane);

            vBox.getChildren().add(messagePane.getAnchorPane());
        }
    }

//    public ScrollPane getScrollPane() {
//        return scrollPane;
//    }

    public VBox getvBox() {
        return vBox;
    }

    public Map<Long, MessagePane> getMessages() {
        return messages;
    }
}
