package com.example.backend.controller;

import com.example.backend.dto.auth.AuthenticationRequestDto;
import com.example.backend.dto.auth.AuthenticationResponseDto;
import com.example.backend.dto.user.UserRequestDto;
import com.example.backend.dto.user.UserResponseDto;
import com.example.backend.entities.ApplicationRole;
import com.example.backend.entities.Profile;
import com.example.backend.entities.User;
import com.example.backend.security.jwt.providers.JwtTokenProvider;
import com.example.backend.security.jwt.user.UserDetailsFactory;
import com.example.backend.services.CompanyService;
import com.example.backend.services.UserCompanyService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private AuthenticationManager authManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private CompanyService companyService;
    private UserCompanyService userCompanyService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider, UserService userService, @Qualifier("customBCryptPasswordEncoder") BCryptPasswordEncoder passwordEncoder, CompanyService companyService,UserCompanyService userCompanyService) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
        this.userCompanyService = userCompanyService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authReqDto)
    {
        try{
            String userLogin = authReqDto.getLogin();
            //authReqDto.setPassword(passwordEncoder.encode(authReqDto.getPassword()));
            User user = userService.findUserByLogin(userLogin);
            if(user == null)
            {
                throw new UsernameNotFoundException("\'" + userLogin + "\'" + " is not registered");
            }
            //if(!user.getPassword().equals(authReqDto.getPassword()))
            if(!passwordEncoder.matches(authReqDto.getPassword(),user.getPassword()))
                throw new BadCredentialsException("Login/password is invalid!");
            Collection<GrantedAuthority> roles = new LinkedList<>();
            roles.add(new SimpleGrantedAuthority(user.getApplicationRole().name()));
            String jwtToken = jwtTokenProvider.createJwtToken(userLogin,roles);
            String refreshToken = jwtTokenProvider.createRefreshToken(userLogin,roles);
            AuthenticationResponseDto responseDto = new AuthenticationResponseDto(jwtToken, refreshToken,user.getId());
            return ResponseEntity.ok().body(responseDto);
        }
        catch (AuthenticationException err)
        {
            //return ResponseEntity.status().
            throw new BadCredentialsException(err.getMessage());
            //return R
        }
    }

    @PostMapping("/renew")
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
                AuthenticationResponseDto authResponseDto = new AuthenticationResponseDto(newAccessToken, newRefreshToken, user.getId());
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto)
    {
        try{

            User user = new User(userRequestDto.getLogin(),userRequestDto.getPassword(), ApplicationRole.USER, new Profile(userRequestDto.getFirst_name(),userRequestDto.getMiddle_name(),userRequestDto.getLast_name()));
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            boolean isApprovedUser = !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(ApplicationRole.USER.name()));
            userService.save(user,userRequestDto.getCompanyId(),isApprovedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(user));
        }
        catch (EntityExistsException err)
        {
            return ResponseEntity.status(409).body(err.getMessage());
        }
        catch (EntityNotFoundException err)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.getMessage());
        }
        //catch (IllegalArgumentException err)
        //{
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        //}
    }
}