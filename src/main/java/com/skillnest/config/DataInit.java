package com.skillnest.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skillnest.models.Curso;
import com.skillnest.models.Empleado;
import com.skillnest.models.Instructor;
import com.skillnest.models.Inscripcion;
import com.skillnest.models.Usuario;
import com.skillnest.repositories.CursoRepository;
import com.skillnest.repositories.EmpleadoRepository;
import com.skillnest.repositories.InstructorRepository;
import com.skillnest.repositories.InscripcionRepository;
import com.skillnest.repositories.UsuarioRepository;

@Configuration
public class DataInit {

	@Bean
	CommandLineRunner initData(
	        UsuarioRepository usuarioRepo,
	        CursoRepository cursoRepo,
	        EmpleadoRepository empleadoRepo,
	        InstructorRepository instructorRepo,
	        InscripcionRepository inscripcionRepo,
	        PasswordEncoder encoder) {

	    return args -> {

	        // ===== Usuarios (login) =====
	        if (usuarioRepo.findByUsername("admin").isEmpty()) {
	            usuarioRepo.save(new Usuario("admin", encoder.encode("Admin123!"), "ADMIN"));
	        }
	        if (usuarioRepo.findByUsername("user").isEmpty()) {
	            usuarioRepo.save(new Usuario("user", encoder.encode("User123!"), "EMPLEADO"));
	        }

	        // obtenemos el usuario EMPLEADO para vincularlo al Empleado
	        Usuario usuarioEmpleado = usuarioRepo.findByUsername("user")
	                .orElseThrow(() -> new IllegalStateException("Usuario 'user' no existe"));

	        // ===== Instructor de ejemplo =====
	        if (instructorRepo.count() == 0) {
	            Instructor instructor = new Instructor();
	            instructor.setNombreCompleto("Juan Pérez");
	            instructor.setEmail("juan.perez@empresa.cl");
	            instructor.setTelefono("+56 9 1111 1111");
	            instructor.setEspecialidad("Java / Spring Boot");
	            instructor.setActivo(true);
	            instructor = instructorRepo.save(instructor);

	            // ===== Cursos de ejemplo =====
	            if (cursoRepo.count() == 0) {
	                Curso curso1 = new Curso();
	                curso1.setNombre("Introducción a Java");
	                curso1.setDescripcion("Fundamentos de Java, POO y buenas prácticas.");
	                curso1.setFechaInicio(LocalDate.now().plusDays(7));
	                curso1.setFechaFin(LocalDate.now().plusDays(37));
	                curso1.setDuracionHoras(24);
	                curso1.setCupoMaximo(20);
	                curso1.setEstado("ABIERTO");
	                curso1.setInstructor(instructor);
	                curso1 = cursoRepo.save(curso1);

	                Curso curso2 = new Curso();
	                curso2.setNombre("Spring Boot para aplicaciones web");
	                curso2.setDescripcion("Creación de APIs y aplicaciones web con Spring Boot.");
	                curso2.setFechaInicio(LocalDate.now().plusDays(15));
	                curso2.setFechaFin(LocalDate.now().plusDays(45));
	                curso2.setDuracionHoras(30);
	                curso2.setCupoMaximo(15);
	                curso2.setEstado("ABIERTO");
	                curso2.setInstructor(instructor);
	                curso2 = cursoRepo.save(curso2);

	                // ===== Empleado de ejemplo =====
	                Empleado empleado;
	                if (empleadoRepo.count() == 0) {
	                    empleado = new Empleado();
	                    empleado.setRut("11111111-1");
	                    empleado.setNombres("Empleado");
	                    empleado.setApellidos("Demo");
	                    empleado.setEmailCorporativo("empleado.demo@empresa.cl");
	                    empleado.setEmailPersonal("empleado.demo@gmail.com");
	                    empleado.setDepartamento("Tecnología");
	                    empleado.setActivo(true);
	                    empleado.setUsuario(usuarioEmpleado); // aquí el link con la tabla usuarios
	                    empleado = empleadoRepo.save(empleado);
	                } else {
	                    empleado = empleadoRepo.findAll().get(0);
	                }

	                // ===== Inscripción de ejemplo =====
	                if (inscripcionRepo.count() == 0) {
	                    Inscripcion insc = new Inscripcion();
	                    insc.setFechaInscripcion(LocalDateTime.now());
	                    insc.setEstado("INSCRITO");
	                    insc.setEmpleado(empleado);
	                    insc.setCurso(curso1);
	                    inscripcionRepo.save(insc);
	                }
	            }
	        }
	    };
	}

}
