package org.adrian.ejemplo01.model;

public class Equipo {
    private final int id;
    private final String nombre;
    private final int anyoFundacion;

    public Equipo(int id, String nombre, int anyoFundacion) {
        this.id = id;
        this.nombre = nombre;
        this.anyoFundacion = anyoFundacion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnyoFundacion() {
        return anyoFundacion;
    }

}
