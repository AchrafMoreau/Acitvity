package com.example.demo.service;

import com.example.demo.dto.UserLoginDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.CustomeException;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.MyUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;


    public String signin(UserLoginDTO user)
    {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            List<UserRole> userRoles = userRepository.findByUsername(user.getUsername()).getUserRoles();
            return jwtTokenProvider.createToken(user.getUsername(), userRoles);
        }catch (AuthenticationException e){
            throw new CustomeException("Invalid Username/password cridentional", HttpStatus.UNAUTHORIZED);
        }
    }

    public String signup(User user)
    {
        if(!userRepository.existsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), userRepository.findByUsername(user.getUsername()).getUserRoles());
        }else{
            throw new CustomeException("Username Already Exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username){
        userRepository.deleteByUsername(username);
    }

    public User search(String username){
        return userRepository.findByUsername(username);
    }

    public User whoami(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        return userRepository.findByUsername(jwtTokenProvider.getUsername(token));
    }

    public String refresh(String name){
        List<UserRole> userRoles = userRepository.findByUsername(name).getUserRoles();
        return jwtTokenProvider.createToken(name, userRoles);
    }


}
