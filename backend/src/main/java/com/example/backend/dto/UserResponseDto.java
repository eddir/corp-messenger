package com.example.backend.dto;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class UserResponseDto
{
    Long id;
    protected String login;
    protected String first_name;
    protected String middle_name;
    protected String last_name;
    @JsonProperty("img_url")
    protected String imgUrl;
    Set<CompanyResponseDto> company;

    public UserResponseDto(){}

    public UserResponseDto(User user)
    {
        this.id = user.getId();
        this.login = user.getLogin();
        this.first_name = user.getProfile().getFirstName();
        this.last_name = user.getProfile().getLastName();
        this.middle_name = user.getProfile().getMiddleName();
        this.imgUrl = user.getImgUrl();
        this.company = new HashSet<>();
        Set<Company> companies = user.getCompanies();
            for(Company comp : companies)
            {
                this.company.add(new CompanyResponseDto(comp));
            }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<CompanyResponseDto> getCompany() {
        return company;
    }

    public void setCompany(Set<CompanyResponseDto> company) {
        this.company = company;
    }
}
