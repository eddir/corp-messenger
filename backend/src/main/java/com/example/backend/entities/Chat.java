package com.example.backend.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "chat")
public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chat_id")
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_dateaa", updatable = false)
    protected Calendar createdDate;

    @Column(name = "title")
    protected String title;

    @Column(name = "description")
    protected String description;

    @Column(name = "img_url")
    protected String imgUrl;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    protected Company companyId;

    @Column(name = "is_public")
    protected Boolean isPublic;

    @Column(name = "is_closed")
    protected Boolean isClosed;

    @Column(name = "is_private")
    protected Boolean isPrivate;

    @Column(name = "is_pinned")
    protected Boolean isPinned;

    // TODO: 16.12.2022 Проверить, использую ОкО в первый раз!
    @OneToOne
    @JoinColumn(name = "last_message_id")
    protected Message lastMessage;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    protected Set<Message> listOfMessages = new HashSet<>();

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    protected List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public Set<Message> getListOfMessages() {
        return listOfMessages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Long getId() {
        return id;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setListOfMessages(Set<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
