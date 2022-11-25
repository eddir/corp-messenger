package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService
{
    protected UserRepo userRepo;

    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepo = userRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public User save(User user)
    {
        user.setPassword((user.getPassword()));
        return userRepo.save(user);
    }

    public List<User> findAll()
    {
        return userRepo.findAll();
    }

    public User findUserByLogin(String login)
    {
        User user = userRepo.findUserByLogin(login);
        return user;
    }
}
