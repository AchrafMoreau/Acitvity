package com.example.demo.controller;

import com.example.demo.dto.UserLoginDTO;
import com.example.demo.exception.CustomeException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String Hello(){
        return "hola";
    }

    @PostMapping("/demo")
    public String demo(){
        return "Post Request";
    }

    @PostMapping("/users/signin")
    public String signin(@RequestBody UserLoginDTO user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            return jwtTokenProvider.createToken(user.getUsername(), userRepository.findByUsername(user.getUsername()).getUserRoles());
        }catch (AuthenticationException e){
            throw new CustomeException("Invalid Username/password cridentional", HttpStatus.valueOf(304));
        }
    }





    @GetMapping("/test")
    public String HelloWord(){
        return "it's actualy restart every time";
    }



}
