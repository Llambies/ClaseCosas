package org.adrian.boletin01;

import java.sql.Date;

public class Ciclista {
    private int id_ciclista;
    private int id_equipo;
    private String nombre;
    private String pais;
    private Date fecha_nac;

    public Ciclista(int id_ciclista, int id_equipo, String nombre, String pais, Date fecha_nac) {
        this.id_ciclista = id_ciclista;
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.pais = pais;
        this.fecha_nac = fecha_nac;
    }

    public int getId_ciclista() {
        return id_ciclista;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }
}
