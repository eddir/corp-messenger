package com.example.backend.dto;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyResponseDto
{
    @JsonProperty("id")
    protected Long id;
    protected String name;
    //protected User userOwner;

    public CompanyResponseDto(){}

    public CompanyResponseDto(Company company)
    {
        this.id = company.getId();
        this.name = company.getName();
        //this.userOwner = company.getUserOwner();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
