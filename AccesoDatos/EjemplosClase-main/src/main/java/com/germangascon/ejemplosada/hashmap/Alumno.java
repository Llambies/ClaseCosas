package com.germangascon.ejemplosada.hashmap;

import java.time.LocalDate;

/**
 * Alumno
 * License: 🅮 Public Domain
 * Created on: 2025-09-25
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Alumno {
    /** Número de identificación del alumno **/
    private final String nia;
    private final String nombre;
    private final String apellido1;
    private final String apellido2;
    private final LocalDate fechaNacimiento;

    public Alumno(String nia, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNia() {
        return nia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Alumno alumno)) return false;

        return nia.equals(alumno.nia);
    }

    @Override
    public int hashCode() {
        return nia.hashCode();
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nia='" + nia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                "}\n";
    }
}
