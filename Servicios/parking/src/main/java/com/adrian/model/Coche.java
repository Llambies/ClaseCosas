package com.adrian.model;

public class Coche implements Runnable {

    static int contador;
    String id;
    Parking parking;

    boolean esElectrico;
    boolean esEspecial;

    public Coche(Parking parking, boolean esElectrico, boolean esEspecial) {
        id = "C" + String.format("%03d", contador);
        this.esElectrico = esElectrico;
        this.esEspecial = esEspecial;

        contador++;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
