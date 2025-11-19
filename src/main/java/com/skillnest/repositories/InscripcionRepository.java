package com.skillnest.repositories;

import com.skillnest.models.Inscripcion;
import com.skillnest.models.Curso;
import com.skillnest.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Todas las inscripciones de un empleado
    List<Inscripcion> findByEmpleado(Empleado empleado);

    // Todas las inscripciones de un curso
    List<Inscripcion> findByCurso(Curso curso);

    // Para evitar inscribir dos veces al mismo empleado en el mismo curso
    Optional<Inscripcion> findByEmpleadoAndCurso(Empleado empleado, Curso curso);
}
