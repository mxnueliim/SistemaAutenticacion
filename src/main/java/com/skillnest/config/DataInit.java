package com.skillnest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skillnest.models.Usuario;
import com.skillnest.repositories.UsuarioRepository;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                repo.save(new Usuario("admin", encoder.encode("Admin123!"), "ADMIN"));
            }
            if (repo.findByUsername("user").isEmpty()) {
                repo.save(new Usuario("user", encoder.encode("User123!"), "EMPLEADO"));
            }
        };
    }
}
