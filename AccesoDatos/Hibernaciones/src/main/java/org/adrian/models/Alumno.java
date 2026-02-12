package org.adrian.models;

public class Alumno {
    private final String nombre;
    private final String nia;

    public Alumno(String nombre, String nia) {
        this.nombre = nombre;
        this.nia = nia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNia() {
        return nia;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Alumno alumno) {
            return this.nombre.equals(alumno.nombre) && this.nia.equals(alumno.nia);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", nia='" + nia + '\'' +
                '}';
    }
}
