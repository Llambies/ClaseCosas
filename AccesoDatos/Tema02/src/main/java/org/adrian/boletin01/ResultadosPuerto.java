package org.adrian.boletin01;

public class ResultadosPuerto {
    private int id_puerto;
    private int id_ciclista;
    private int posicion;
    private int puntos;

    public ResultadosPuerto(int id_puerto, int id_ciclista, int posicion, int puntos) {
        this.id_puerto = id_puerto;
        this.id_ciclista = id_ciclista;
        this.posicion = posicion;
        this.puntos = puntos;
    }

    public int getId_puerto() {
        return id_puerto;
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
