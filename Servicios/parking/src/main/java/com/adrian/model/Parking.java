package com.adrian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;

public class Parking {

    final String nombre;

    public Semaphore escritor = new Semaphore(1, true);
    public List<String> eventos;
    public int coches;
    public int cochesCompletados;

    public int totalPlazas;
    public Semaphore plazas;
    public int totalPlazasElectricas;
    public Semaphore plazasElectricas;
    public int totalPlazasEspeciales;
    public Semaphore plazasEspeciales;

    final double precio;
    final double precioElectrico;
    final double precioEspecial;
    double recaudacion;
    public Semaphore recaudador = new Semaphore(1, true);
    public EscuchadorParkings escuchador;

    final int accesos;
    public List<Coche> cochesEntrando;
    public Semaphore entradas;
    public List<Coche> cochesSaliendo;
    public Semaphore salidas;

    public Parking(
            String nombre,
            int coches,
            int plazas,
            int plazasElectricas,
            int plazasEspeciales,
            double precio,
            double precioElectrico,
            double precioEspecial,
            int accesos) {

        this.nombre = nombre;

        eventos = new ArrayList<>();

        this.coches = coches;
        this.cochesCompletados = 0;

        totalPlazas = plazas;
        this.plazas = new Semaphore(plazas);
        totalPlazasElectricas = plazasElectricas;
        this.plazasElectricas = new Semaphore(plazasElectricas);
        totalPlazasEspeciales = plazasEspeciales;
        this.plazasEspeciales = new Semaphore(plazasEspeciales);

        this.precio = precio;
        this.precioElectrico = precioElectrico;
        this.precioEspecial = precioEspecial;

        this.accesos = accesos;
        entradas = new Semaphore(accesos, true);
        cochesEntrando = new ArrayList<>();
        salidas = new Semaphore(accesos, true);
        cochesSaliendo = new ArrayList<>();
    }

    public void pagar(Coche c, long tiempo) {
        if (c.esEspecial) {
            recaudacion += precioEspecial * tiempo / 10000;
        } else if (c.esElectrico) {
            recaudacion += precioElectrico * tiempo / 10000;
        } else {
            recaudacion += precio * tiempo / 10000;
        }
        System.out.println(recaudacion + "$");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                escuchador.cambioRecaudacion(recaudacion);
            }
        });

    }

    @Override
    public String toString() {
        return nombre + ", plazas: " + plazas;
    }

}
