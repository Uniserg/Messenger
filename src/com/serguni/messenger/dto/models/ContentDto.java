package com.serguni.messenger.dto.models;

//import java.io.Serial;
import java.io.Serializable;

public class ContentDto extends ContentPKDto implements Serializable {
//    @Serial
    private static final long serialVersionUID = 1;
    private MessageDto message;

//    public ContentDto(Content content) {
//        message = new MessageDto(content.getMessage());
//    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }
}
