package com.example.backend.security.jwt.providers;

import com.example.backend.entities.ApplicationRole;
import com.example.backend.security.jwt.exception.JwtAuthenticationException;
import com.example.backend.security.jwt.exception.JwtExpiredException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
public class JwtTokenProvider
{

    @Value("${jwt.token.secret.jwt}")
    private String secretJwt;

    @Value("${jwt.token.secret.refresh}")
    private String secretRefresh;

    @Value("${jwt.token.expired.jwt}")
    private long timeOfValideJwtToken;

    @Value("${jwt.token.expired.refresh}")
    private long timeOfValideRefreshToken;

    private UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(@Qualifier("jwtUserDetailsService")UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    private void init()
    {
        this.secretJwt = Base64.getEncoder().encodeToString(secretJwt.getBytes());
        this.secretRefresh = Base64.getEncoder().encodeToString(secretJwt.getBytes());
    }

    public String createJwtToken(String login,Collection<? extends GrantedAuthority> roles)
    {
        Claims claim = Jwts.claims().setSubject(login);
        /*
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority : applicationRole)
        {
            roles.add(authority.getAuthority());
        }
        */

        claim.put("role",roles);
        Date now = new Date();
        Date valid = new Date(now.getTime() + timeOfValideJwtToken);

        return Jwts.builder()
                            .setClaims(claim)
                            .setIssuedAt(now)
                            .setExpiration(valid)
                            .signWith(SignatureAlgorithm.HS256,secretJwt)
                            .compact();
    }

    public String createRefreshToken(String login,Collection<? extends GrantedAuthority> applicationRole)
    {
        Claims claim = Jwts.claims().setSubject(login);
        /*
        List<String> roles = new ArrayList<>();
        for(GrantedAuthority authority : applicationRole)
        {
            roles.add(authority.getAuthority());
        }
         */
        claim.put("role", applicationRole);

        Date now = new Date();
        Date valid = new Date(now.getTime() + timeOfValideRefreshToken);
        return Jwts.builder()
                .setClaims(claim)
                .setIssuedAt(now)
                .setExpiration(valid)
                .signWith(SignatureAlgorithm.HS256,secretJwt)
                .compact();
    }

    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getLogin(String token)
    {
        return parserBuilder().setSigningKey(secretJwt).build().parseClaimsJws(token).getBody().getSubject();
        //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req)
    {
        String prefix = "Bearer ";
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith(prefix))
        {
            return bearerToken.substring(prefix.length(),bearerToken.length());
        }
        return null;
    }

    public boolean isValidJwtToken(String token) throws JwtAuthenticationException
    {
        try
        {
            Jws<Claims> claims =  Jwts.parserBuilder().setSigningKey(secretJwt).build().parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date()))
                throw new JwtException("Jwt token is expired");
        }
        catch(JwtException err)
        {
            throw new JwtAuthenticationException("JWT token is expired or invalid", err);
            //Вот тут нужно будет по refresh-token' выдать новый JWT
        }
        return true;
    }


    public boolean isValidRefreshToken(String token)
    {
        try
        {
            Jws<Claims> claims =  Jwts.parserBuilder().setSigningKey(secretRefresh).build().parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date()))
                throw new JwtException("refresh token is expired");
        }
        catch(JwtException err)
        {
            throw new JwtAuthenticationException("refresh token is expired or invalid", err);
            //Вот тут нужно будет по refresh-token' выдать новый JWT
        }
        return true;
    }

    private String getRole(ApplicationRole applicationRole)
    {
        return applicationRole.name();
    }

    private List<String> getRoleNames(List<ApplicationRole> appRoles)
    {
        List<String> listRoles = new ArrayList<>(appRoles.size());
        for(int i = 0; i < appRoles.size(); i++)
        {
            listRoles.add(appRoles.get(i).name());
        }
        return listRoles;
    }

}
