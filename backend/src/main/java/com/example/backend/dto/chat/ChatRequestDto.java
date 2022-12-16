package com.example.backend.dto.chat;

import com.example.backend.entities.Chat;

public class ChatRequestDto
{
    protected Long id;
    protected String name;
    protected Long companyId;
    protected Boolean isPinned;
    protected Boolean isPrivate;
    protected String imgUrl;
    protected String type;

    public ChatRequestDto()
    {
    }

    public ChatRequestDto(Chat chat) {
        this.id = chat.getId();
        this.name = chat.getTitle();
        this.companyId = chat.getCompanyId().getId();
        this.isPinned = chat.getPinned();
        this.isPrivate = chat.getPrivate();
        this.imgUrl = chat.getImgUrl();
        this.type = chat.getType().name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
