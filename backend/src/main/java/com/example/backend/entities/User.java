package com.example.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity
{
    @Column(name = "login")
    protected String login;

    @Column(name = "password")
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_role")
    protected ApplicationRole applicationRole;

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
