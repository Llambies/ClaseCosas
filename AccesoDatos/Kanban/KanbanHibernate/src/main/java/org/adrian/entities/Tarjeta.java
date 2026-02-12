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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarjetas")
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "columna_id", nullable = false)
    private Columna columna;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tarjeta_etiqueta", joinColumns = @JoinColumn(name = "tarjeta_id"), inverseJoinColumns = @JoinColumn(name = "etiqueta_id"))
    private List<Etiqueta> etiquetas;

    // Constructor vacío requerido por Hibernate
    public Tarjeta() {
    }

    public Tarjeta(String titulo, String descripcion, Columna columna) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.columna = columna;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Columna getColumna() {
        return columna;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setColumna(Columna columna) {
        this.columna = columna;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }
    
    @Override
    public String toString() {
        return "Tarjeta [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", columnaId=" + (columna != null ? columna.getId() : null) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarjeta tarjeta = (Tarjeta) o;
        return Objects.equals(id, tarjeta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
