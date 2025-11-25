package org.adrian.parkineo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Coche implements Runnable {
    private static int contadorGlobal = 0;
    private int id;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private boolean estacionado;
    private boolean esElectrico;
    private boolean esDiscapacitado;
    private Parking parking;
    private long tiempoEstacionamiento;

    private double tarifa;

    public Coche(Parking parking) {
        this.id = ++contadorGlobal;
        this.estacionado = false;
        this.esElectrico = Math.random() < 0.3; // 30% de probabilidad
        this.esDiscapacitado = Math.random() < 0.1; // 10% de probabilidad
        this.parking = parking;
        this.tiempoEstacionamiento = 0;
        this.tarifa = parking.tarifaBase * (esElectrico ? parking.ratioElectrico : 1)
                * (esDiscapacitado ? parking.ratioDiscapacitado : 1);
    }

    public void registrarEntrada() {
        this.horaEntrada = LocalDateTime.now();
        this.estacionado = true;
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
        this.estacionado = false;
        calcularTarifa();
    }

    @Override
    public void run() {
        // Intentar entrar al estacionamiento
        if (parking.intentarEntrada(this)) {
            // Esperar tiempo aleatorio estacionado (entre 2s y 8s)
            tiempoEstacionamiento = 2000 + (long) (Math.random() * 6000);
            try {
                Thread.sleep(tiempoEstacionamiento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Salir del estacionamiento
            parking.registrarSalida(this);
        } else {
            // Si no puede entrar, espera y reintenta
            try {
                Thread.sleep(500 + (long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void calcularTarifa() {
        if (horaEntrada != null && horaSalida != null) {
            long minutos = java.time.temporal.ChronoUnit.MINUTES.between(horaEntrada, horaSalida);
            // Tarifa: 2€ por hora, mínimo 1€
            this.tarifa = Math.max(1.0, (minutos / 60.0) * tarifa);
        }
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public double getTarifa() {
        return tarifa;
    }

    public boolean isEstacionado() {
        return estacionado;
    }

    public boolean isElectrico() {
        return esElectrico;
    }

    public boolean isDiscapacitado() {
        return esDiscapacitado;
    }

    public String getEstado() {
        if (!estacionado && horaEntrada == null) {
            return "En espera";
        } else if (estacionado) {
            return "Estacionado";
        } else {
            return "Salió";
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder tipo = new StringBuilder();
        if (esElectrico)
            tipo.append("[ELÉCTRICO] ");
        if (esDiscapacitado)
            tipo.append("[DISCAPACITADO] ");
        return String.format("Coche #%d - %s%s - Entrada: %s, Salida: %s, Tarifa: €%.2f",
                id,
                tipo.toString(),
                getEstado(),
                horaEntrada != null ? horaEntrada.format(formatter) : "N/A",
                horaSalida != null ? horaSalida.format(formatter) : "N/A",
                tarifa);
    }

    public static void resetContador() {
        contadorGlobal = 0;
    }
}
