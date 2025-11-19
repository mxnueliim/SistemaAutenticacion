package com.skillnest.controllers;

import com.skillnest.models.Curso;
import com.skillnest.models.Empleado;
import com.skillnest.models.Inscripcion;
import com.skillnest.models.Usuario;
import com.skillnest.repositories.CursoRepository;
import com.skillnest.repositories.EmpleadoRepository;
import com.skillnest.repositories.InscripcionRepository;
import com.skillnest.repositories.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleado/cursos")
public class EmpleadoCursosController {

    private final CursoRepository cursoRepo;
    private final EmpleadoRepository empleadoRepo;
    private final UsuarioRepository usuarioRepo;
    private final InscripcionRepository inscripcionRepo;

    public EmpleadoCursosController(CursoRepository cursoRepo,
                                    EmpleadoRepository empleadoRepo,
                                    UsuarioRepository usuarioRepo,
                                    InscripcionRepository inscripcionRepo) {
        this.cursoRepo = cursoRepo;
        this.empleadoRepo = empleadoRepo;
        this.usuarioRepo = usuarioRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    // Listar cursos disponibles + mis inscripciones
    @GetMapping
    public String verCursos(@AuthenticationPrincipal UserDetails user, Model model) {

        // 1) Buscar Usuario por username
        Usuario usuario = usuarioRepo.findById(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 2) Buscar Empleado asociado
        Empleado empleado = empleadoRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("No hay empleado asociado al usuario"));

        // 3) Cursos disponibles (ej: PROGRAMADO)
        List<Curso> cursos = cursoRepo.findByEstado("PROGRAMADO");

        // 4) Mis inscripciones
        List<Inscripcion> misInscripciones = inscripcionRepo.findByEmpleado(empleado);

        model.addAttribute("empleado", empleado);
        model.addAttribute("cursos", cursos);
        model.addAttribute("misInscripciones", misInscripciones);

        return "empleado/cursos-lista"; // templates/empleado/cursos-lista.html
    }

    // Inscribirse en un curso
    @PostMapping("/{cursoId}/inscribirse")
    public String inscribirse(@PathVariable Long cursoId,
                              @AuthenticationPrincipal UserDetails user) {

        Usuario usuario = usuarioRepo.findById(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Empleado empleado = empleadoRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("No hay empleado asociado al usuario"));

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        // Evitar doble inscripción
        Optional<Inscripcion> yaExiste =
                inscripcionRepo.findByEmpleadoAndCurso(empleado, curso);

        if (yaExiste.isEmpty()) {
            Inscripcion insc = new Inscripcion();
            insc.setEmpleado(empleado);
            insc.setCurso(curso);
            insc.setFechaInscripcion(LocalDateTime.now());
            insc.setEstado("INSCRITO");
            inscripcionRepo.save(insc);
        }

        return "redirect:/empleado/cursos";
    }
}
