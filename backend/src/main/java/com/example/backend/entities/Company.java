package com.example.backend.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "company_id")
    Long id;

    @Column(name = "name")
    protected String name;

    //@ManyToMany(mappedBy = "companies")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_company",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    protected Set<User> users = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner", nullable = false)
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    public void addUserIntoCompany(User user)
    {
        users.add(user);
        user.addCompany(this);
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
