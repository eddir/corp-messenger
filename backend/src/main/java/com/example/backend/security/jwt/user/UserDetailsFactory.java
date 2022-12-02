package com.example.backend.security.jwt.user;

import com.example.backend.entities.User;
import com.example.backend.security.JwtUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsFactory
{
    public static JwtUser convertUserToUserDetails(User user)
    {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getApplicationRole().name());
        return new JwtUser(user.getId(), user.getLogin(), user.getPassword(), grantedAuthority);
    }

}
