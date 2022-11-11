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


}
