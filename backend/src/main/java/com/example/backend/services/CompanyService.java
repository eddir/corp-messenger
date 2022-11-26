package com.example.backend.services;

import com.example.backend.entities.Company;
import com.example.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService
{
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll()
    {
        return companyRepository.findAll();
    }

    public Company save(Company company)
    {
        return companyRepository.save(company);
    }
}
