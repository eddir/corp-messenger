package com.example.backend.services;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Service
public class CompanyService
{
    private CompanyRepository companyRepository;
    private UserCompanyService userCompanyService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, UserCompanyService userCompanyService) {
        this.companyRepository = companyRepository;
        this.userCompanyService = userCompanyService;
    }

    public List<Company> findAll()
    {
        return companyRepository.findAll();
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional
    public Company save(Company company) throws EntityExistsException
    {
        if(companyRepository.getCompanyByName(company.getName()) != null)
            throw new EntityExistsException("Компания с таким названием уже существует.");
        company = companyRepository.save(company);
        userCompanyService.addUserToCompany(company.getUserOwner(), company, true);
        //company.addUserIntoCompany(company.getUserOwner());
        return company;
    }

    /*
    @Transactional
    public User addUserIntoCompany(User user, Long companyId) throws EntityNotFoundException
    {
        Company company = companyRepository.getCompanyById(companyId);
        if(company == null)
            throw new EntityNotFoundException("Компания с id = \'" + companyId + "\'не существует.");
        company.addUserIntoCompany();
        return user;
    }

     */

    @Transactional
    public Company getCompanyById(Long id)
    {
        return companyRepository.getCompanyById(id);
    }

    @Transactional
    public Company getCompanyByName(String name)
    {
        return companyRepository.getCompanyByName(name);
    }
}
