package org.adrian.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "contraseña", nullable = false)
    private String contraseña;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Tablero> tableros;

    // Constructor vacío requerido por Hibernate
    public Usuario() {
    }

    public Usuario(String nombre, String email, String contraseña, List<Tablero> tableros) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tableros = tableros;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public List<Tablero> getTableros() {
        return tableros;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}