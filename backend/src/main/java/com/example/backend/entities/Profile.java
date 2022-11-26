package com.example.backend.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Profile
{
    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "middle_name", nullable = true)
    protected String middleName;

    @Column(name = "last_name")
    protected String lastName;

    public Profile(){}

    public Profile(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
