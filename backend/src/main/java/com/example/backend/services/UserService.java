package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    protected UserRepo userRepo;
    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepo = userRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public User register(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
