package com.example.demo.dto;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;

import java.util.List;

public class UserRegisterDTO {

    private String username;
    private String password;
    private String email;
    private List<UserRole> userRoles;

    public void setUserRoles(List<UserRole> roles){
        this.userRoles = roles;
    }
    public List<UserRole> getUserRoles(){
        return userRoles;
    }
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
