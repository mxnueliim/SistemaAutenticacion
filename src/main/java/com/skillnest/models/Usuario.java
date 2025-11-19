package com.skillnest.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 60, nullable = false) // BCrypt ~60
    private String password;

    @Column(name = "role", length = 20, nullable = false) // valores: USER, ADMIN
    private String role;

    public Usuario() {}

    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
