package com.example.backend.security.filters;

import com.example.backend.security.jwt.exception.JwtAuthenticationException;
import com.example.backend.security.jwt.providers.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
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

            HttpServletResponseWrapper httpResponse = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            httpResponse.sendError(401, "unauthorized");
            servletResponse = httpResponse;


            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
            //((HttpServletResponse)servletResponse).sendError(401);
            //throw err;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
