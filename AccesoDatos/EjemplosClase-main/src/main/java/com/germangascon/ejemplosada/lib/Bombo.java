package com.germangascon.ejemplosada.lib;

import java.util.Random;

/**
 * Bombo
 * License: 🅮 Public Domain
 * Created on: 2025-09-25
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Bombo {
    private final int[] numeros;
    private int cantidad;

    public Bombo(int cantidad, int min) {
        numeros = new int[cantidad];
        this.cantidad = cantidad;
        int valor = min;
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = valor++;
        }
    }

    public int getBola() {
        Random random = new Random();
        int posicion = random.nextInt(cantidad);
        int bola = numeros[posicion];
        numeros[posicion] = numeros[cantidad -1];
        numeros[cantidad -1] = bola;
        cantidad--;
        return bola;
    }

    public void reset() {
        cantidad = numeros.length;
    }


}
