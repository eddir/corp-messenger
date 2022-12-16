package com.example.backend.dto.chat;

import com.example.backend.dto.message.MessageResponseDto;
import com.example.backend.entities.Chat;
import com.example.backend.entities.Member;
import com.example.backend.entities.Message;
import com.example.backend.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Iterator;

//Не для поиска!Хотя...
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
    protected MessageResponseDto lastMessage;

    protected Boolean isPinned;

    protected Boolean isPrivate;

    public ChatResponseDto(Chat chat, Message lastMessage) {
        this.id = chat.getId();
        this.name = chat.getTitle();
        this.imgUrl = chat.getImgUrl();
        this.companyId = chat.getCompanyId().getId();
        //TODO: 16.12.2022 Поправить
        this.unread = 0;
        // TODO: 16.12.2022 Поправить
        this.lastMessage = new MessageResponseDto(lastMessage);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterator<Member> iter = chat.getMembers().iterator();
        while(iter.hasNext())
        {
            Member mem = iter.next();
            if(mem.getUser().getLogin().equals(userName))
            {
                if(mem.getPinned() != null && mem.getPinned().booleanValue())
                    this.isPinned = true;
            }
        }
        //if(this.isPinned == null)
        //    throw new RuntimeException("Юзер хочет получить информацию о чатах в которых не состоит!");
        this.isPrivate = chat.getPrivate();
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
