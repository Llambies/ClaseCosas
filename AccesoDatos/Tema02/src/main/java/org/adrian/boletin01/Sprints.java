package org.adrian.boletin01;

public class Sprints {
    private int id_sprint;
    private int id_etapa;
    private double km;

    public Sprints(int id_sprint, int id_etapa, double km) {
        this.id_sprint = id_sprint;
        this.id_etapa = id_etapa;
        this.km = km;
    }

    public int getId_sprint() {
        return id_sprint;
    }

    public int getId_etapa() {
        return id_etapa;
    }

    public double getKm() {
        return km;
    }
}
