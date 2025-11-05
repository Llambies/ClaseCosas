package org.adrian.boletin01;

import java.sql.Date;

public class Etapa {
    private int id_etapa;
    private int id_carrera;
    private int num_etapa;
    private Date fecha;
    private String salida;
    private String llegada;
    private double distancia_km;
    private TipoEtapa tipo;

    public Etapa(int id_etapa, int id_carrera, int num_etapa, Date fecha, String salida, String llegada,
            double distancia_km, TipoEtapa tipo) {
        this.id_etapa = id_etapa;
        this.id_carrera = id_carrera;
        this.num_etapa = num_etapa;
        this.fecha = fecha;
        this.salida = salida;
        this.llegada = llegada;
        this.distancia_km = distancia_km;
        this.tipo = tipo;
    }

    public int getId_etapa() {
        return id_etapa;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public int getNum_etapa() {
        return num_etapa;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getSalida() {
        return salida;
    }

    public String getLlegada() {
        return llegada;
    }

    public double getDistancia_km() {
        return distancia_km;
    }

    public TipoEtapa getTipo() {
        return tipo;
    }
}

enum TipoEtapa {
    Llana,
    Quebrada,
    Montaña,
    CRI,
    CRE,
}