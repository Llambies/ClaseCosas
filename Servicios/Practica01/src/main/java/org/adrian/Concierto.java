package org.adrian;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Concierto {
    private String nombre;
    private int totalEntradas;
    private double precioEntrada;
    private int comprasConcurrentes;

    private Semaphore taquillas;
    private Semaphore entradas;
    private Semaphore controlDeAcceso = new Semaphore(1, true);
    private List<Fan> fansEsperando;
    private List<Fan> fansEnTaquilla;

    public Concierto(String nombre, int totalEntradas, double precioEntrada, int comprasConcurrentes) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del concierto no puede estar vacio");
        }
        if (totalEntradas <= 0) {
            throw new IllegalArgumentException("El total de entradas debe ser mayor a 0");
        }
        if (precioEntrada <= 0) {
            throw new IllegalArgumentException("El precio de la entrada debe ser mayor a 0");
        }
        if (comprasConcurrentes <= 0) {
            throw new IllegalArgumentException("El numero de compras concurrentes debe ser mayor a 0");
        }

        this.nombre = nombre;
        this.totalEntradas = totalEntradas;
        this.precioEntrada = precioEntrada;
        this.comprasConcurrentes = comprasConcurrentes;

        this.taquillas = new Semaphore(comprasConcurrentes, true);
        this.entradas = new Semaphore(totalEntradas, true);

        this.fansEsperando = new ArrayList<>();
        this.fansEnTaquilla = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalEntradas() {
        return totalEntradas;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public int getComprasConcurrentes() {
        return comprasConcurrentes;
    }

    public Semaphore getTaquillas() {
        return taquillas;
    }

    public Semaphore getEntradas() {
        return entradas;
    }

    public Semaphore getControlDeAcceso() {
        return controlDeAcceso;
    }
    
    public List<Fan> getFansEsperando() {
        return fansEsperando;
    }

    public List<Fan> getFansEnTaquilla() {
        return fansEnTaquilla;
    }

}
