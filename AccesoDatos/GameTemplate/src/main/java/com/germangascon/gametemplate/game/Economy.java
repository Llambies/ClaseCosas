package com.germangascon.gametemplate.game;

public class Economy {
    private static Economy instance;
    private int dinero;

    private Economy() {
        dinero = 100;
    }

    public static Economy getInstance() {
        if (instance == null) {
            instance = new Economy();
        }
        return instance;
    }

    public int getDinero() {
        return dinero;
    }

    public void ganarDinero(int dinero) {
        this.dinero += dinero;
    }

    public boolean tengoSuficienteDinero(int dineroAGastar) {
        return this.dinero >= dineroAGastar;
    }

    public void gastarDinero(int dinero) {
        this.dinero -= dinero;
    }
}
