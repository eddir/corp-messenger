package com.example.backend.security;

import com.example.backend.entities.ApplicationRole;
import com.example.backend.security.jwt.JwtConfig;
import com.example.backend.security.jwt.providers.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    JwtTokenProvider jwtTokenProvider;
    JwtUserDetailsService jwtUserDetailsService;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtUserDetailsService jwtUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/admin/**").hasAnyRole(ApplicationRole.ADMIN.name(), ApplicationRole.SUPER_ADMIN.name())
                    .antMatchers("/api/super-admin/**").hasRole(ApplicationRole.SUPER_ADMIN.name())
                    .antMatchers("/api/users").authenticated()
                    .antMatchers("/api/auth").permitAll();
        http.apply(new JwtConfig(jwtTokenProvider));

    }


}
