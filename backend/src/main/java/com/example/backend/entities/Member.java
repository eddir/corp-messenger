package com.example.backend.entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Immutable
public class Member
{


    @EmbeddedId
    protected PrimaryKey id = new PrimaryKey();

    @ManyToOne
    @JoinColumn(name = "chat_id",insertable = false,updatable = false)
    protected Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    protected User user;

    protected Boolean isOwner;
    protected Boolean isAdmin;
    protected Boolean isPinned;

    public Member(Chat chat, User user, Boolean isOwner, Boolean isAdmin, Boolean isPinned)
    {
        this.chat = chat;
        this.user = user;
        this.isOwner = isOwner;
        this.isAdmin = isAdmin;
        this.isPinned = isPinned;

        this.id.chatId = chat.id;
        this.id.userId = user.id;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }
}
