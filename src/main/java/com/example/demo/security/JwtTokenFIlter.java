package com.example.demo.security;

import com.example.demo.exception.CustomeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.rmi.ServerException;

public class JwtTokenFIlter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFIlter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try{
            if(token != null && jwtTokenProvider.validateToken(token)){
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch(IOException | ServletException e){
            SecurityContextHolder.clearContext();
            throw new ServletException("Error proccessing FIlter ", e);
        }

    }
}

