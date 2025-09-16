package com.adrian.tema01.boletin01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Ej07 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 7");
        GestionArchivos3 ga = new GestionArchivos3();
        ga.leerBinario("./src/main/java/com/adrian/tema01/boletin01", "Ej07.bin");
    }
}

class GestionArchivos3 extends GestionArchivos2 {
    void leerBinario(String directorio, String archivo) {
        File f = new File(directorio, archivo);
        try (FileInputStream fis = new FileInputStream(f)) {
            int byteLeido;
            System.out.println("Contenido en hexadecimal:");
            while ((byteLeido = fis.read()) != -1) {
                // Use %02X to format the byte as a two-digit hexadecimal string
                System.out.printf("%02X ", byteLeido);
            }
            System.out.println(); // New line for clean output
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}