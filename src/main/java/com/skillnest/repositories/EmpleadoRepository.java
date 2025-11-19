package com.skillnest.repositories;

import com.skillnest.models.Empleado;
import com.skillnest.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Para encontrar al empleado a partir del usuario logueado
    Optional<Empleado> findByUsuario(Usuario usuario);
}
