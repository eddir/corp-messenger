package com.example.backend.services;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo)
    {
        this.userRepo = userRepo;
    }

    public User register(User user)
    {
        return userRepo.save(user);
    }
}
