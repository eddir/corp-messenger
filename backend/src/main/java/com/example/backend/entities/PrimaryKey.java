package com.example.backend.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PrimaryKey implements Serializable
{
    @Column(name = "chat_id")
    protected Long chatId;

    @Column(name = "user_id")
    protected Long userId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PrimaryKey(){}

    public PrimaryKey(Long chatId, Long userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return (int)(chatId % (Integer.MAX_VALUE));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PrimaryKey))
            return false;
        if(obj == this)
            return true;
        PrimaryKey buf = (PrimaryKey) obj;
        if(this.chatId.equals(buf.chatId) && this.userId.equals(buf.userId))
            return true;
        return false;
    }
}
