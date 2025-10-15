package com.adrian.tema01.boletin02;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ej07 {
    public static void main(String[] args) {
        CalculadoraPrimosMultihilo cp = new CalculadoraPrimosMultihilo();
        Thread hilo1 = new Thread(cp::calcularSiguiente);
        Thread hilo2 = new Thread(cp::calcularSiguiente);
        Thread hilo3 = new Thread(cp::calcularSiguiente);
        Thread hilo5 = new Thread(cp::calcularSiguiente);
        Thread hilo7 = new Thread(cp::calcularSiguiente);
        Thread hilo6 = new Thread(cp::calcularSiguiente);

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo5.start();
        hilo6.start();
        hilo7.start();

        cp.guardarLista();
    }
}

class CalculadoraPrimosMultihilo {
    private static List<Integer> primos = Collections.synchronizedList(new ArrayList<>());

    public CalculadoraPrimosMultihilo() {
        primos = GestorNumeros.leerNumeros();
    }

    public synchronized void calcularSiguiente() {
        if (primos.isEmpty()) {
            primos.add(2);
            return;
        }

        int siguiente = primos.get(primos.size() - 1) + 1;
        while (!esPrimo(siguiente)) {
            siguiente++;
        }
        primos.add(siguiente);

    }

    synchronized boolean esPrimo(int numero) {
        for (Integer primo : primos) {
            if (numero % primo == 0) {
                return false;
            }
        }
        return true;
    }

    public void guardarLista() {
        GestorNumeros.guardarNumeros((ArrayList<Integer>) primos);
    }
}