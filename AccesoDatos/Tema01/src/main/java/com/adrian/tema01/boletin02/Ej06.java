package com.adrian.tema01.boletin02;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Ej06 {
    public static void main(String[] args) {
        CalculadoraPrimos cp = new CalculadoraPrimos();
        cp.calcularSiguiente();
    }
}

class CalculadoraPrimos {
    private static ArrayList<Integer> primos;

    public CalculadoraPrimos() {
        primos = GestorNumeros.leerNumeros();
    }

    public void calcularSiguiente() {
        if (primos.isEmpty()) {
            primos.add(2);
            GestorNumeros.guardarNumeros(primos);
            return;
        }

        int siguiente = primos.get(primos.size() - 1) + 1;
        while (!esPrimo(siguiente)) {
            siguiente++;
        }
        primos.add(siguiente);
        GestorNumeros.guardarNumeros(primos);

    }

    boolean esPrimo(int numero) {
        for (Integer primo : primos) {
            if (numero % primo == 0) {
                return false;
            }
        }
        return true;
    }
}

class GestorNumeros {
    private static final String FILE_NAME = "./src/main/java/com/adrian/tema01/boletin02/primos.txt";

    public static void guardarNumeros(ArrayList<Integer> numeros) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (Integer numero : numeros) {
                writer.write(numero.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error escribiendo archivo: " + e.getMessage());
        }
    }

    public static ArrayList<Integer> leerNumeros() {
        File file = new File(FILE_NAME);
        ArrayList<Integer> numeros = new ArrayList<>();
        if (file.exists()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    numeros.add(Integer.parseInt(sc.nextLine()));
                }
            } catch (IOException e) {
                System.out.println("Error leyendo archivo: " + e.getMessage());
            }
        }
        return numeros;
    }
}