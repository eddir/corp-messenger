package com.example.backend.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;

public class Chat
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "chat_id")
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
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

    @Column(name = "is_public")
    protected Boolean isClosed;

    @Column(name = "is_public")
    protected Boolean isPrivate;

    @Column(name = "is_public")
    protected Boolean isPinned;

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
}
