package com.example.backend.entities;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_company")
public class UserCompany
{
    @Embeddable
    public static class Id implements Serializable
    {
        @Column(name = "user_id")
        protected Long userId;

        @Column(name = "company_id")
        protected Long companyId;

        public Id(){}

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }

        @Override
        public int hashCode() {
            return (int)(userId % (Integer.MAX_VALUE - 1));
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Id))
                return false;
            if(obj == this)
                return true;
            Id buf = (Id)obj;
            if(buf.userId.equals(this.userId) && (buf.companyId.equals(this.companyId)))
                return true;
            return false;
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @ManyToOne
    @JoinColumn(name = "user_id",updatable = false, insertable = false)
    protected User user;

    @ManyToOne
    @JoinColumn(name = "company_id",updatable = false,insertable = false)
    protected Company company;

    @Column(name = "is_approved", nullable = false)
    protected boolean isApproved = false;

    public UserCompany(){}

    public UserCompany(User user, Company company, boolean isApproved)
    {
        this.id.userId = user.id;
        this.id.companyId = company.id;
        this.isApproved = isApproved;
    }

    public Id getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Company getCompany() {
        return company;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
