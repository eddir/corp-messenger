package com.example.backend.services;

import com.example.backend.entities.Company;
import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class UserService
{
    protected UserRepository userRepository;
    protected CompanyService companyService;
    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Qualifier("customBCryptPasswordEncoder") BCryptPasswordEncoder bCryptPasswordEncoder, CompanyService companyService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.companyService = companyService;
    }


    public User save(User user) throws EntityExistsException
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.findUserByLogin(user.getLogin()) != null)
            throw new EntityExistsException("Пользователь с данным логином уже зарегистрирован!");
        return userRepository.save(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPER_ADMIN')")
    public User save(User user,Long companyId) throws EntityExistsException
    {
        save(user);
        Company company = companyService.getCompanyById(companyId);
        if(company == null)
            throw new EntityNotFoundException("Компания с id = " + companyId + " не существует.");
        company.addUserIntoCompany(user);
        return user;
    }

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public User findUserByLogin(String login)
    {
        User user = userRepository.findUserByLogin(login);
        return user;
    }

    public User findUserById(Long id)
    {
        User user = userRepository.findUserById(id);
        return user;
    }
}
