package org.adrian.practica01fx;

import java.util.concurrent.Semaphore;

public class Concierto {

    private String nombre;
    private int aforo; // Cantidad de entradas disponibles
    private double precio; // Precio de la entrada
    private int fans; // Cantidad de personas que van
    private int taquillas; // Cantidad de compras en paralelo

    private Semaphore semaphore;
    private int entradasDisponibles;

    public Concierto(String nombre, int aforo, double precio, int fans, int taquillas) {
        this.nombre = nombre;
        this.aforo = aforo;
        this.precio = precio;
        this.fans = fans;
        this.taquillas = taquillas;

        semaphore = new Semaphore(1, true);
        entradasDisponibles = aforo;
    }

    public boolean venderEntrada() {
        try {
            semaphore.acquire();
            if (entradasDisponibles > 0) {
                entradasDisponibles--;
                semaphore.release();
                return true;
            }
            semaphore.release();
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public int getEntradasDisponibles() {
        return entradasDisponibles;
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

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

}
