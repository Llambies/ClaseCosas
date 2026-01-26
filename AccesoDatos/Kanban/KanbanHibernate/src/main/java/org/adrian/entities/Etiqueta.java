package org.adrian.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "etiquetas")
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "color", nullable = false)
    private String color;

    // Constructor vacío requerido por Hibernate
    public Etiqueta() {
    }

    public Etiqueta(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Etiqueta [id=" + id + ", nombre=" + nombre + ", color=" + color + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return Objects.equals(id, etiqueta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
   
    
}
