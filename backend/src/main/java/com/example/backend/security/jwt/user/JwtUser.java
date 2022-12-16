package com.example.backend.security.jwt.user;

import com.example.backend.entities.ApplicationRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JwtUser implements UserDetails {

    private Long id;
    private String login;
    private String password;
    private GrantedAuthority authorities;

    JwtUser(Long id, String login, String password, GrantedAuthority authorities)
    {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        LinkedList<GrantedAuthority> authority = new LinkedList<GrantedAuthority>();
        authority.add(authorities);
        return authority;
        /*
        return new LinkedList<GrantedAuthority>(){
            {
                add(authorities);
            }
        };

         */
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
