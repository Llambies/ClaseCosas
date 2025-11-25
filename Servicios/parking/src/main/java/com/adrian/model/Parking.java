package com.adrian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Parking {

    final String nombre;

    Semaphore controlAcceso = new Semaphore(1, true);
    List<Coche> cochesEsperando;

    final int plazas;
    final int plazasElectricas;
    final int plazasEspeciales;
    List<Coche> cochesAparcados;
    List<Coche> cochesElectricosAparcados;
    List<Coche> cochesEspecialesAparcados;

    final double precio;
    final double precioElectrico;
    final double precioEspecial;
    double recaudacion;
    Semaphore recaudador = new Semaphore(1, true);

    final int accesos;
    Semaphore entradas;
    Semaphore salidas;

    public Parking(
            String nombre,
            int plazas,
            int plazasElectricas,
            int plazasEspeciales,
            double precio,
            double precioElectrico,
            double precioEspecial,
            int accesos) {

        this.nombre = nombre;

        this.plazas = plazas;
        cochesAparcados = new ArrayList<Coche>();
        this.plazasElectricas = plazasElectricas;
        cochesElectricosAparcados = new ArrayList<Coche>();
        this.plazasEspeciales = plazasEspeciales;
        cochesEspecialesAparcados = new ArrayList<Coche>();

        this.precio = precio;
        this.precioElectrico = precioElectrico;
        this.precioEspecial = precioEspecial;

        this.accesos = accesos;
        entradas = new Semaphore(accesos, true);
        salidas = new Semaphore(accesos, true);

    }

    public boolean haySitio(Coche c) {
        if (c.esEspecial) {
            if (cochesEspecialesAparcados.size() < plazasEspeciales) {
                return true;
            }
        }
        if (c.esElectrico) {
            if (cochesElectricosAparcados.size() < plazasElectricas) {
                return true;
            }
        }
        return cochesAparcados.size() < plazas;
    }

    public void aparcar(Coche c) {
        if (c.esEspecial) {
            cochesEspecialesAparcados.add(c);
        }
        if (c.esElectrico) {
            cochesElectricosAparcados.add(c);
        }
        cochesAparcados.add(c);
    }

    @Override
    public String toString() {
        return nombre + ", plazas: " + plazas;
    }

}
