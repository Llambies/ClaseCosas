package com.llambies;

public class Plaza {
    private int numero;
    private int planta;
    private String tipo;

    public Plaza(int numero, int planta, String tipo) {
        this.numero = numero;
        this.planta = planta;
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public int getPlanta() {
        return planta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }
    
}
