package com.serguni.messenger.dto.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public abstract class ContentPKDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    protected long msgId;
    protected long id;


    public ContentPKDto() {
    }

    public ContentPKDto(long msgId, long id) {
        this.msgId = msgId;
        this.id = id;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentPKDto contentPK = (ContentPKDto) o;
        return msgId == contentPK.msgId && id == contentPK.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgId, id);
    }
}

