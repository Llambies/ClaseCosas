package com.adrian.primavera.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alumno")
@Getter
@Setter
@NoArgsConstructor // Obligatorio para JPA
@AllArgsConstructor
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(unique = true, nullable = false)
    private String email;
    private Integer edad;
    @Column(nullable = false)
    private Boolean aprobado = false;

    public Alumno(String nombre, String email, Integer edad) {
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.aprobado = false;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public Boolean getAprobado() {
        return aprobado;
    }
    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
}