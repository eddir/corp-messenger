package com.example.backend.security.filters;

import com.example.backend.security.jwt.exception.JwtAuthenticationException;
import com.example.backend.security.jwt.providers.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JwtTokenFilter extends GenericFilterBean
{
    JwtTokenProvider tokenProvider;

    public JwtTokenFilter(JwtTokenProvider tokenProvider)
    {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String token = tokenProvider.resolveToken(httpRequest);
        try{
            if(token != null)
            {
                if(tokenProvider.isValidJwtToken(token))
                {
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    //Или тут мы юзаем refresh-token для обновления JWT
                    if(authentication != null) //Объект не может быть null вообще... убрать проверку?
                    {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        catch (JwtAuthenticationException err)
        {
            throw err;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
