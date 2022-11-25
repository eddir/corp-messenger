package com.example.backend.controller;

import com.example.backend.entities.User;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/users")
public class UserController
{
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }





}
