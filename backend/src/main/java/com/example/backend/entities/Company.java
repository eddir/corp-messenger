package com.example.backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "company_id")
    Long id;

    @Column(name = "name")
    protected String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    protected User userOwner;

    public Company(){}

    public Company(String name, User userOwner) {
        this.id = id;
        this.name = name;
        this.userOwner = userOwner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }
}
