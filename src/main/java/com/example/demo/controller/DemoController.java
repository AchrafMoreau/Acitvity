package com.example.demo.controller;

import com.example.demo.dto.UserLoginDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import com.example.demo.exception.CustomeException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;

    private ModelMapper modelMapper;

    public DemoController(){
        this.modelMapper = new ModelMapper();
    }

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
        return userService.signin(user);
    }

    @PostMapping("/users/signup")
    public String signup(@RequestBody User user){
        return userService.signup(user);
    }

    @DeleteMapping("/{username}")
    public String delete(@PathVariable String username){
        System.out.println("Hello "+ username);
        try{
            userService.delete(username);
            return "User Deleted Successfully";
        }catch(Exception e){
            throw new CustomeException("User Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}")
    public UserResponseDTO search(@PathVariable String username){
        System.out.println("------------username : " + username);
        User user = userService.search(username);
        if(user == null){
            throw new CustomeException("User Not Found", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @GetMapping("/me")
    public UserResponseDTO whoami(HttpServletRequest req){
        User user = userService.whoami(req);
        if(user == null){
            throw new CustomeException("User Not FOund", HttpStatus.NOT_FOUND);
        }
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest req){
        return userService.refresh(req.getRemoteUser());
    }

    @GetMapping("/test")
    public String HelloWord(){
        return "it's actualy restart every time";
    }



}
