package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_CLIENT, ROLE_ADMIN;

    public String getAuthority(){
        return name();
    }
}
