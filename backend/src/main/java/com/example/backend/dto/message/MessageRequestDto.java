package com.example.backend.dto.message;

import com.example.backend.entities.Message;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class MessageRequestDto
{
    @JsonProperty("child_id")
    protected Set<Long> child = new HashSet<>();
    protected String text;

    @JsonProperty("user_id")
    protected Long userId;

    @JsonProperty("chat_id")
    protected Long chatId;
    protected Boolean isPinned;

    public MessageRequestDto(){}

    /*
    public MessageRequestDto(Message message) {
        this.child = message.getChildsMessage();
        this.text = message.getText();
        this.destination_user_id = message.getSender();
        this.chatId = message.getChat().getId();
        this.isPinned = isPinned;
        this.isRead = isRead;
    }

     */

    public Set<Long> getChild() {
        return child;
    }

    public void setChild(Set<Long> child) {
        this.child = child;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    @JsonProperty("is_pinned")
    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

}
