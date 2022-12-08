package com.example.backend.controller;

import com.example.backend.dto.UserResponseDto;
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


    @GetMapping
    public ResponseEntity<List<User>> users()
    {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id)
    {
        User user = userService.findUserById(id);
        //Если User не найден, какой код ошибки выкидывать?
        if(user == null)
            ResponseEntity.ok().body(null);
        return ResponseEntity.ok().body(new UserResponseDto(user));
    }

}
