package com.skillnest.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;

    @Column(length = 20, nullable = false)
    private String estado; // INSCRITO, APROBADO, REPROBADO, CANCELADO

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    public Inscripcion() {}
    
    // Getters y setters...

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaInscripcion() {
		return fechaInscripcion;
	}

	public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

    
}
