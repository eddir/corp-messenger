package com.example.backend.dto.company;

import com.example.backend.dto.user.UserResponseDto;
import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.entities.UserCompany;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyResponseDto
{
    @JsonProperty("id")
    protected Long id;
    protected String name;
    @JsonProperty("owner_company")
    protected Long user;

    public CompanyResponseDto(){}

    public CompanyResponseDto(Company company)
    {
        this.id = company.getId();
        this.name = company.getName();
        this.user = company.getUserOwner().getId();
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

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
