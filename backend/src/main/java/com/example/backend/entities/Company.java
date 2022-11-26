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
}
