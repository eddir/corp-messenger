package com.example.backend.services;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.entities.UserCompany;
import com.example.backend.repositories.UserCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UserCompanyService
{
    protected UserCompanyRepository userCompanyRepository;

    @Autowired
    public UserCompanyService(UserCompanyRepository userCompanyRepository)
    {
        this.userCompanyRepository = userCompanyRepository;
    }

    @Transactional
    public void addUserToCompany(User user, Company company, boolean isApproved)
    {
        UserCompany userCompany = new UserCompany(user, company, isApproved);
        userCompanyRepository.save(userCompany);
    }
}
