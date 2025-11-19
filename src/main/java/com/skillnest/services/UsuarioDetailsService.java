package com.skillnest.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.skillnest.models.Usuario;
import com.skillnest.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> opt = repo.findByUsername(username);
        Usuario u = opt.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // En BD guarda roles como: USER o ADMIN
        return User.withUsername(u.getUsername())
                   .password(u.getPassword()) // BCrypt en BD
                   .roles(u.getRole())
                   .build();
    }
}
