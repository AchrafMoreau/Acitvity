package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimal Username charachter is 4")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 8, max = 255, message = "Minimal password is 8 Charachters")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    List<UserRole> userRoles;


    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public @Size(min = 8, max = 255, message = "Minimal password is 8 Charachters") String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public @Size(min = 4, max = 255, message = "Minimal Username charachter is 4") String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(@Size(min = 4, max = 255, message = "Minimal Username charachter is 4") String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(@Size(min = 8, max = 255, message = "Minimal password is 8 Charachters") String password) {
        this.password = password;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
