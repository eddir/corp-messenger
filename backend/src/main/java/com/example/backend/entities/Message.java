package com.example.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "message")
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    protected Calendar createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_date")
    protected Calendar updatedDate;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    protected User sender;

    @ManyToOne()
    @JoinColumn(name = "chat_id")
    protected Chat chat;

    protected String text; //Возможно стоит заменить дефолтную реализацию на sql тип Text

    @Column(name = "is_pinned")
    protected Boolean isPinned;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

    public Message(){}

    public Long getId() {
        return id;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
