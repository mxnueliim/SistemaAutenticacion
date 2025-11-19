package com.skillnest.controllers;

import com.skillnest.models.Curso;
import com.skillnest.repositories.CursoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*") // Permite llamadas desde otros frontends
public class ApiCursosController {

    private final CursoRepository cursoRepo;

    public ApiCursosController(CursoRepository cursoRepo) {
        this.cursoRepo = cursoRepo;
    }

    @GetMapping
    public List<Curso> listarCursosDisponibles() {
        // Solo cursos PROGRAMADO para "disponibles"
        return cursoRepo.findByEstado("PROGRAMADO");
    }
}
