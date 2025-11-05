package org.adrian.boletin01;

public class ResultadosSprint {
    private int id_sprint;
    private int id_ciclista;
    private int posicion;
    private int puntos;

    public ResultadosSprint(int id_sprint, int id_ciclista, int posicion, int puntos) {
        this.id_sprint = id_sprint;
        this.id_ciclista = id_ciclista;
        this.posicion = posicion;
        this.puntos = puntos;
    }

    public int getId_sprint() {
        return id_sprint;
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
