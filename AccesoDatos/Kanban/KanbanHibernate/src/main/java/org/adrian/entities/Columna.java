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
@Table(name = "columnas")
public class Columna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tablero_id", nullable = false)
    private Tablero tablero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "columna")
    private List<Tarjeta> tarjetas;

    // Constructor vacío requerido por Hibernate
    public Columna() {
    }
    
    public Columna(String nombre, Tablero tablero) {
        this.nombre = nombre;
        this.tablero = tablero;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }
    
    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    @Override
    public String toString() {
        return "Columna [id=" + id + ", nombre=" + nombre + ", tableroId=" + (tablero != null ? tablero.getId() : null) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Columna columna = (Columna) o;
        return Objects.equals(id, columna.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
