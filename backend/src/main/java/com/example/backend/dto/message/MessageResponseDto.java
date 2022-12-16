package com.example.backend.dto.message;

import com.example.backend.entities.Message;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MessageResponseDto
{
    protected Long id;


    @JsonProperty("created_at")
    protected Calendar createdAt;

    @JsonProperty("updated_at")
    protected Calendar updatedAt;

    protected String text;

    @JsonProperty("user_id")
    protected Long userId;

    @JsonProperty("chat_id")
    protected Long chatId;

    @JsonProperty("is_pinned")
    protected Boolean isPinned;

    @JsonProperty("is_read")
    protected Boolean isRead;

    @JsonProperty("child_messages")
    protected Set<MessageResponseDto> messageChildsDto = new HashSet<MessageResponseDto>();

    public MessageResponseDto(Message message)
    {
        this.id = message.getId();
        for(Message mes : message.getChildsMessage())
        {
            this.messageChildsDto.add(new MessageResponseDto(mes));
        }
        this.text = message.getText();
        this.userId = message.getSender().getId();
        this.chatId = message.getChat().getId();
        this.isPinned = message.getPinned();
        //Если НИКТО не прочитал - false
        this.isRead = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MessageResponseDto> getMessageChildsDto() {
        return messageChildsDto;
    }

    public void setMessageChildsDto(Set<MessageResponseDto> messageChildsDto) {
        this.messageChildsDto = messageChildsDto;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
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

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}

