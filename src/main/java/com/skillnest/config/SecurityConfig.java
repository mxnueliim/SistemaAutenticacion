package com.skillnest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/admin/**").hasRole("ADMIN")
              .requestMatchers("/empleado/**").hasRole("EMPLEADO")
              .requestMatchers("/perfil/**", "/panel/**").authenticated()
              .requestMatchers("/api/**").authenticated()
              .requestMatchers("/", "/login", "/error",
                               "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
              .anyRequest().permitAll()
          )
          .formLogin(login -> login
              .loginPage("/login")
              .defaultSuccessUrl("/panel", true)
              .permitAll()
          )
          .logout(logout -> logout
        		    .logoutUrl("/logout")
        		    .logoutSuccessUrl("/login?logout=1")
        		    .permitAll()
        		)
          .httpBasic(Customizer.withDefaults())
          .csrf(Customizer.withDefaults());
          //.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
