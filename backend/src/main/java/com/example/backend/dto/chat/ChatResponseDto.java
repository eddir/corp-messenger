package com.example.backend.dto.chat;

import com.example.backend.entities.Chat;
import com.example.backend.entities.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;

public class ChatResponseDto
{
    protected Long id;

    protected String name;

    @JsonProperty("img_url")
    protected String imgUrl;

    @JsonProperty("company_id")
    protected Long companyId;

    protected Integer unread;

    @JsonProperty("last_message")
    protected Message lastMessage;

    protected Boolean isPinned;

    protected Boolean isPrivate;

    public ChatResponseDto(Chat chat)
    {

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }


    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @GetMapping("is_pinned")
    public Boolean getPinned() {
        return isPinned;
    }

    @GetMapping("is_private")
    public Boolean getPrivate() {
        return isPrivate;
    }
}
