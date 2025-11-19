package com.skillnest.repositories;

import com.skillnest.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Ejemplo para listar solo cursos "PROGRAMADO"
    List<Curso> findByEstado(String estado);
}
