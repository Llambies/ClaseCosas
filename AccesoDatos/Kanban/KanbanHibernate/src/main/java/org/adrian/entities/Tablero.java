package org.adrian.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tableros")
public class Tablero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tablero")
    private List<Columna> columnas;

    // Constructor vacío requerido por Hibernate
    public Tablero() {
    }

    public Tablero(String nombre, Usuario usuario, List<Columna> columnas) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.columnas = columnas;
    }

    public Long getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public List<Columna> getColumnas() {
        return columnas;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return "Tablero [id=" + id + ", nombre=" + nombre + ", usuarioId=" + (usuario != null ? usuario.getId() : null) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tablero tablero = (Tablero) o;
        return Objects.equals(id, tablero.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
