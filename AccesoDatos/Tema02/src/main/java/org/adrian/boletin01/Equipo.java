package org.adrian.boletin01;

public class Equipo {
    private int id_equipo;
    private String nombre;
    private String pais;

    public Equipo(int id_equipo, String nombre, String pais) {
        this.id_equipo = id_equipo;
        this.nombre = nombre;
        this.pais = pais;
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
}
