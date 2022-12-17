package com.example.backend.dto.member;

import com.example.backend.entities.Member;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberResponseDto
{
    protected Long userId;
    protected Long chatId;
    protected Boolean isAdmin;
    protected Boolean isOwner;
    protected Boolean isPinned;

    public MemberResponseDto(Member member) {
        this.userId = member.getUser().getId();
        this.chatId = member.getChat().getId();
        this.isAdmin = member.getAdmin();
        this.isOwner = member.getOwner();
        this.isPinned = member.getPinned();
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("isAdmin")
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @JsonProperty("isOwner")
    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    @JsonProperty("isPinned")
    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }
}
