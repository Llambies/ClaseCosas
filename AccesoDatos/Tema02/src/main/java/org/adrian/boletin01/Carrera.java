package org.adrian.boletin01;

import java.sql.Date;

public class Carrera {
    private int id_carrera;
    private String nombre;
    private int anio;
    private Date fecha_inicio;
    private Date fecha_fin;

    public Carrera(int id_carrera, String nombre, int anio, Date fecha_inicio, Date fecha_fin) {
        this.id_carrera = id_carrera;
        this.nombre = nombre;
        this.anio = anio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio() {
        return anio;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }
}
