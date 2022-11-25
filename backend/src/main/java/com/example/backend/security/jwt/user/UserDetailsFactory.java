package com.example.backend.security.jwt.user;

import com.example.backend.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsFactory
{
    public static UserDetails convertUserToUserDetails(User user)
    {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getApplicationRole().name());
        return new JwtUser(user.getId(), user.getLogin(), user.getPassword(), grantedAuthority);
    }

}
