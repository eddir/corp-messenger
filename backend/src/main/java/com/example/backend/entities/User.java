package com.example.backend.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "login", unique = true, nullable = false)
    protected String login;

    @Column(name = "password", nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_role", nullable = false)
    protected ApplicationRole applicationRole;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    protected Calendar create;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "last_update")
    protected Calendar lastUpdate;

    public User() {

    }

    public User(String login, String password, ApplicationRole applicationRole) {
        this.login = login;
        this.password = password;
        this.applicationRole = applicationRole;
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
