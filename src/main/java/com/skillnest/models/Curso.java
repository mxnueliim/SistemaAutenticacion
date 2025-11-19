package com.skillnest.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;

    @Column(name = "cupo_maximo")
    private Integer cupoMaximo;

    @Column(length = 20)
    private String estado; // PROGRAMADO, EN_CURSO, FINALIZADO, CANCELADO

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<Inscripcion> inscripciones;

    public Curso() {}
    
    // Getters y setters...

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getDuracionHoras() {
		return duracionHoras;
	}

	public void setDuracionHoras(Integer duracionHoras) {
		this.duracionHoras = duracionHoras;
	}

	public Integer getCupoMaximo() {
		return cupoMaximo;
	}

	public void setCupoMaximo(Integer cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}

    
}
