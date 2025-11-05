package org.adrian.boletin01;

public class Puerto {
    private int id_puerto;
    private int id_etapa;
    private String nombre;
    private double km;
    private CategoriaPuerto categoria;

    public Puerto(int id_puerto, int id_etapa, String nombre, double km, String categoria) {
        this.id_puerto = id_puerto;
        this.id_etapa = id_etapa;
        this.nombre = nombre;
        this.km = km;
        try {
            this.categoria = CategoriaPuerto.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException e) {
            try {
                this.categoria = CategoriaPuerto.values()[Integer.parseInt(categoria)];
            } catch (NumberFormatException nfe) {
                this.categoria = null;
            }
        }
    }

    public int getId_puerto() {
        return id_puerto;
    }

    public int getId_etapa() {
        return id_etapa;
    }

    public String getNombre() {
        return nombre;
    }

    public double getKm() {
        return km;
    }

    public CategoriaPuerto getCategoria() {
        return categoria;
    }
}