package org.adrian.boletin01;

public class ResultadosEtapa {
    private int id_etapa;
    private int id_ciclista;
    private int posicion;
    private String tiempo;
    private String diferencia;
    private String estado;

    public ResultadosEtapa(int id_etapa, int id_ciclista, int posicion, String tiempo, String diferencia, String estado) {    
        this.id_etapa = id_etapa;
        this.id_ciclista = id_ciclista;
        this.posicion = posicion;
        this.tiempo = tiempo;
        this.diferencia = diferencia;
        this.estado = estado;
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

    public String getTiempo() {
        return tiempo;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public String getEstado() {
        return estado;
    }
}
