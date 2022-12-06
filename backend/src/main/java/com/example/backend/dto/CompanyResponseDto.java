package com.example.backend.dto;

import com.example.backend.entities.Company;

public class CompanyResponseDto
{
    protected Long id;
    protected String name;

    public CompanyResponseDto(){}

    public CompanyResponseDto(Company company)
    {
        this.id = company.getId();
        this.name = company.getName();
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
