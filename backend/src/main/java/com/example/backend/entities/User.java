package com.example.backend.entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    protected Calendar create;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_visit_date")
    protected Calendar lastVisitDate;

    @Embedded
    protected Profile profile;

    @Column(name = "login", unique = true, nullable = false)
    protected String login;

    @Column(name = "password", nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    protected ApplicationRole applicationRole;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    protected Set<Company> companies = new HashSet<>();

    @Column(name = "img_url")
    protected String imgUrl;

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public void addCompany(Company company)
    {
        this.companies.add(company);
    }

    public Calendar getCreate() {
        return create;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public Calendar getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Calendar lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Profile getProfile() {
        if(this.profile == null)
            return Profile.emptyProfile;
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "last_update")
    protected Calendar lastUpdate;

    public User() {}

    public User(String login, String password, ApplicationRole applicationRole) {
        this.login = login;
        this.password = password;
        this.applicationRole = applicationRole;
    }

    public User(String login, String password, ApplicationRole applicationRole, Profile profile) {
        this.login = login;
        this.password = password;
        this.applicationRole = applicationRole;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationRole getApplicationRole() {
        return applicationRole;
    }

    public void setApplicationRole(ApplicationRole applicationRole) {
        this.applicationRole = applicationRole;
    }



}
