package com.skillnest.controllers;

import com.skillnest.models.Curso;
import com.skillnest.repositories.CursoRepository;
import com.skillnest.repositories.InstructorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/cursos")
public class CursoAdminController {

    private final CursoRepository cursoRepo;
    private final InstructorRepository instructorRepo;

    public CursoAdminController(CursoRepository cursoRepo, InstructorRepository instructorRepo) {
        this.cursoRepo = cursoRepo;
        this.instructorRepo = instructorRepo;
    }

    @GetMapping
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoRepo.findAll();
        model.addAttribute("cursos", cursos);
        return "admin/cursos-lista"; // templates/admin/cursos-lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("instructores", instructorRepo.findAll());
        return "admin/cursos-form"; // templates/admin/cursos-form.html
    }

    @PostMapping
    public String guardarCurso(@ModelAttribute("curso") Curso curso,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("instructores", instructorRepo.findAll());
            return "admin/cursos-form";
        }
        // Si no se setea estado, por defecto PROGRAMADO
        if (curso.getEstado() == null || curso.getEstado().isBlank()) {
            curso.setEstado("PROGRAMADO");
        }
        cursoRepo.save(curso);
        return "redirect:/admin/cursos";
    }

    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + id));
        model.addAttribute("curso", curso);
        model.addAttribute("instructores", instructorRepo.findAll());
        return "admin/cursos-form";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id) {
        cursoRepo.deleteById(id);
        return "redirect:/admin/cursos";
    }
}
