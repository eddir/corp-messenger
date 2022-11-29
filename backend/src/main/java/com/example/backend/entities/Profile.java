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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
