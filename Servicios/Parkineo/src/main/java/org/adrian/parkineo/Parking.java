package org.adrian.parkineo;

import java.util.concurrent.Semaphore;

public class Parking {
    public int plazasNormales;
    public int plazasElectricas;
    public int plazasDiscapacitados;
    public int numEntradas;
    public int numSalidas;
    public int cochesEnEstacionamiento;
    public int cochesEnEstacionamientoElectricos;
    public int cochesEnEstacionamientoDiscapacitados;
    public int cochesEnEspera;
    public int totalAccesos;
    public double recaudacionTotal;
    public float tarifaBase;
    public float ratioElectrico;
    public float ratioDiscapacitado;

    public boolean simulacionActiva;

    private Semaphore controlEntradas;
    private Semaphore controlSalidas;

    public Parking(int plazasNormales, int plazasElectricas, int plazasDiscapacitados, int numEntradas,
            int numSalidas) {
        this.plazasNormales = plazasNormales;
        this.plazasElectricas = plazasElectricas;
        this.plazasDiscapacitados = plazasDiscapacitados;
        this.numEntradas = numEntradas;
        this.numSalidas = numSalidas;

        this.cochesEnEstacionamiento = 0;
        this.cochesEnEstacionamientoElectricos = 0;
        this.cochesEnEstacionamientoDiscapacitados = 0;
        this.cochesEnEspera = 0;
        this.totalAccesos = 0;
        this.recaudacionTotal = 0;
        this.tarifaBase = 2;
        this.ratioElectrico = 1.5f;
        this.ratioDiscapacitado = 0.5f;

        this.controlEntradas = new Semaphore(numEntradas);
        this.controlSalidas = new Semaphore(numSalidas);
    }

    /**
     * Inicia la simulación creando el número indicado de coches y manteniendo
     * la simulación activa durante el tiempo especificado.
     *
     * @param numCoches    número total de coches que participarán en la simulación
     * @param tiempoSegundos duración aproximada de la simulación en segundos
     */
    public void iniciarSimulacion(int numCoches, int tiempoSegundos) {
        simulacionActiva = true;

        Thread simulacionThread = new Thread(() -> {
            Thread[] hilosCoches = new Thread[numCoches];

            for (int i = 0; i < numCoches; i++) {
                Coche coche = new Coche(this);
                Thread hilo = new Thread(coche, "Coche-" + coche.getId());
                hilosCoches[i] = hilo;
                hilo.start();
            }

            try {
                // La simulación se mantiene activa durante el tiempo indicado
                Thread.sleep(Math.max(0, tiempoSegundos) * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                simulacionActiva = false;
            }
        }, "Simulacion-Parking");

        simulacionThread.setDaemon(true);
        simulacionThread.start();
    }

    public boolean isSimulacionActiva() {
        return simulacionActiva;
    }

    public boolean intentarEntrada(Coche coche) {
        try {
            controlEntradas.acquire();
            if (coche.isElectrico() && plazasElectricas - cochesEnEstacionamientoElectricos > 0) {
                cochesEnEstacionamientoElectricos++;
            } else if (coche.isDiscapacitado() && plazasDiscapacitados - cochesEnEstacionamientoDiscapacitados > 0) {
                cochesEnEstacionamientoDiscapacitados++;
            } else if (plazasNormales - cochesEnEstacionamiento > 0) {
                cochesEnEstacionamiento++;
            } else {
                cochesEnEspera++;
                return false;
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            controlEntradas.release();
        }
    }

    public void registrarSalida(Coche coche) {
        try {
            controlSalidas.acquire();
            if (coche.isElectrico()) {
                cochesEnEstacionamientoElectricos--;
            } else if (coche.isDiscapacitado()) {
                cochesEnEstacionamientoDiscapacitados--;
            } else {
                cochesEnEstacionamiento--;
            }
            recaudacionTotal += coche.getTarifa();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } finally {
            controlSalidas.release();
        }
    }

}
