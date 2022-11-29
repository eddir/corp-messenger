package com.example.backend.security;

import com.example.backend.entities.User;
import com.example.backend.security.jwt.user.UserDetailsFactory;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService
{
    private UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findUserByLogin(login);
        if(user == null)
            throw new UsernameNotFoundException("Login " + "\"" + login + "\" not found into system");
        return UserDetailsFactory.convertUserToUserDetails(user);
    }

}
