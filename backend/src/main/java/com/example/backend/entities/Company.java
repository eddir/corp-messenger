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

    @Column(name = "name",unique = true)
    protected String name;

    //@ManyToMany(mappedBy = "companies")
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    protected Set<UserCompany> usersCompany = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner", nullable = true)
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



    public void addUserIntoCompany(UserCompany userCompany)
    {
        //Добавить новую запись в UserCompany
        this.usersCompany.add(userCompany);
        //throw new RuntimeException("НЕЛЬЗЯ");
       // usersCompany.add(usersCompany);
        //user.addCompany(this);
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
