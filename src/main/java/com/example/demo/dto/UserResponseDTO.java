package com.example.demo.dto;

import com.example.demo.model.UserRole;

import java.util.List;

public class UserResponseDTO {

    private String username;
    private String email;
    private List<UserRole> userRoles;

    public String getUsername() {
        return username;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
