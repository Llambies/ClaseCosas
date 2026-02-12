package org.adrian.models;

import java.util.List;

public class Curso {
    private final String id;
    private final String nombre;

    private final List<Alumno> alumnos;

    public Curso(String id, String nombre, List<Alumno> alumnos) {
        this.id = id;
        this.nombre = nombre;
        this.alumnos = alumnos;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Curso curso) {
            return this.id.equals(curso.id) && this.nombre.equals(curso.nombre);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
