package com.example.backend.controller;

import com.example.backend.dto.AuthenticationRequestDto;
import com.example.backend.dto.AuthenticationResponseDto;
import com.example.backend.entities.User;
import com.example.backend.security.JwtUserDetailsService;
import com.example.backend.security.jwt.providers.JwtTokenProvider;
import com.example.backend.security.jwt.user.UserDetailsFactory;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private AuthenticationManager authManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authReqDto)
    {
        try{
            String userLogin = authReqDto.getLogin();
            User user = userService.findUserByLogin(userLogin);
            if(user == null)
            {
                throw new UsernameNotFoundException("\'" + userLogin + "\'" + " is not registered");
            }
            if(!user.getPassword().equals(authReqDto.getPassword()))
                throw new BadCredentialsException("Login/password is invalid!");
            Collection<GrantedAuthority> roles = new LinkedList<>();
            roles.add(new SimpleGrantedAuthority(user.getApplicationRole().name()));
            String jwtToken = jwtTokenProvider.createJwtToken(userLogin,roles);
            String refreshToken = jwtTokenProvider.createRefreshToken(userLogin,roles);
            AuthenticationResponseDto responseDto = new AuthenticationResponseDto(jwtToken, refreshToken);
            return ResponseEntity.ok().body(responseDto);
        }
        catch (AuthenticationException err)
        {
            //return ResponseEntity.status().
            throw new BadCredentialsException(err.getMessage());
            //return R
        }
    }

    @GetMapping("/renew")
    public ResponseEntity<?> refresh(@RequestBody Map<String,String> body)
    {
        try {
            String accessToken = body.get("accessToken");
            String refreshToken = body.get("refreshToken");
            if (jwtTokenProvider.isValidRefreshToken(refreshToken)) {
                String userLogin = jwtTokenProvider.getLoginFromRefreshToken(refreshToken);
                User user = userService.findUserByLogin(userLogin);
                if (user == null)
                    throw new UsernameNotFoundException("User is deleted");
                UserDetails userDetails = UserDetailsFactory.convertUserToUserDetails(user);
                String newAccessToken = jwtTokenProvider.createJwtToken(userLogin, userDetails.getAuthorities());
                String newRefreshToken = jwtTokenProvider.createRefreshToken(userLogin, userDetails.getAuthorities());
                AuthenticationResponseDto authResponseDto = new AuthenticationResponseDto(newAccessToken, newRefreshToken);
                return ResponseEntity.ok().body(authResponseDto);
            }
        }
        catch (AuthenticationException err)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err.getMessage());
        }
        catch (IllegalArgumentException err)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
        throw new RuntimeException("Что-то пошло не так...");
    }

}
