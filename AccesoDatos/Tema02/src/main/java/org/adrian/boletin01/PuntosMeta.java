package org.adrian.boletin01;

public class PuntosMeta {
    private int id_etapa;
    private int id_ciclista;
    private int posicion;
    private int puntos;

    public PuntosMeta(int id_etapa, int id_ciclista, int posicion, int puntos) {
        this.id_etapa = id_etapa;
        this.id_ciclista = id_ciclista;
        this.posicion = posicion;
        this.puntos = puntos;
    }

    public int getId_etapa() {
        return id_etapa;
    }

    public int getId_ciclista() {
        return id_ciclista;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getPuntos() {
        return puntos;
    }
}
