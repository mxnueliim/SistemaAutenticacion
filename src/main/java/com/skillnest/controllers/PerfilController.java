package com.skillnest.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {
    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("usuario", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        return "perfil"; // templates/perfil.html
    }
}
