package org.adrian.practica01fx;

import javafx.application.Application;

public class Concierto {

    private String nombre;
    private int aforo;
    private double precio;
    private int taquillas;

    public Concierto(String nombre, int aforo, double precio, int taquillas) {
        this.nombre = nombre;
        this.aforo = aforo;
        this.precio = precio;
        this.taquillas = taquillas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTaquillas() {
        return taquillas;
    }

    public void setTaquillas(int taquillas) {
        this.taquillas = taquillas;
    }

}
