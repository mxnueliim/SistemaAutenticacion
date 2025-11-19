package com.skillnest.controllers;

import com.skillnest.models.*;
import com.skillnest.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class ApiInscripcionesController {

    private final UsuarioRepository usuarioRepo;
    private final EmpleadoRepository empleadoRepo;
    private final CursoRepository cursoRepo;
    private final InscripcionRepository inscripcionRepo;

    public ApiInscripcionesController(UsuarioRepository usuarioRepo,
                                      EmpleadoRepository empleadoRepo,
                                      CursoRepository cursoRepo,
                                      InscripcionRepository inscripcionRepo) {
        this.usuarioRepo = usuarioRepo;
        this.empleadoRepo = empleadoRepo;
        this.cursoRepo = cursoRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    // DTO simple para recibir el cursoId
    public static class InscripcionRequest {
        private Long cursoId;

        public Long getCursoId() {
            return cursoId;
        }

        public void setCursoId(Long cursoId) {
            this.cursoId = cursoId;
        }
    }

    @PostMapping
    public ResponseEntity<?> inscribirEmpleado(@AuthenticationPrincipal UserDetails user,
                                               @RequestBody InscripcionRequest request) {

        // Solo dejamos que EMPLEADO use esto (extra por seguridad)
        boolean esEmpleado = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLEADO"));

        if (!esEmpleado) {
            return ResponseEntity.status(403).body("Solo los empleados pueden inscribirse.");
        }

        if (request.getCursoId() == null) {
            return ResponseEntity.badRequest().body("cursoId es requerido.");
        }

        Usuario usuario = usuarioRepo.findById(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Empleado empleado = empleadoRepo.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("No hay empleado asociado al usuario"));

        Curso curso = cursoRepo.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        // Evitar doble inscripción
        Optional<Inscripcion> yaExiste =
                inscripcionRepo.findByEmpleadoAndCurso(empleado, curso);

        if (yaExiste.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("El empleado ya está inscrito en este curso.");
        }

        Inscripcion insc = new Inscripcion();
        insc.setEmpleado(empleado);
        insc.setCurso(curso);
        insc.setFechaInscripcion(LocalDateTime.now());
        insc.setEstado("INSCRITO");

        inscripcionRepo.save(insc);

        return ResponseEntity.ok("Inscripción registrada correctamente.");
    }
}
