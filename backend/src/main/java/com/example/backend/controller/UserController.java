package com.example.backend.controller;

import com.example.backend.dto.user.UserResponseDto;
import com.example.backend.entities.User;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController()
@RequestMapping("/api/users")
public class UserController
{
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponseDto>> users()
    {
        List<UserResponseDto> listUserResponse = new LinkedList<>();
        List<User> userList = userService.findAll();
        for(User user: userList)
        {
            listUserResponse.add(new UserResponseDto(user));
        }
        return ResponseEntity.ok().body(listUserResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        User user = userService.findUserById(id);
        //Если User не найден, какой код ошибки выкидывать?
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }



}
