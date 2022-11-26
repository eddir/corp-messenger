package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@Transactional
public class UserService
{
    protected UserRepository userRepository;

    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public User save(User user) throws EntityExistsException
    {
        user.setPassword((user.getPassword()));
        if(userRepository.findUserByLogin(user.getLogin()) != null)
            throw new EntityExistsException("Пользователь с данным логином уже зарегистрирован!");
        return userRepository.save(user);
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
}