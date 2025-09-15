package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Scanner;

public class Ej07 {
    public static void main(String[] args) {
        GestionArchivos3 ga = new GestionArchivos3();
        ga.leerBinario("./src/main/java/com/adrian/tema01/boletin01", "Ej07.bin");
    }
}

class GestionArchivos3 extends GestionArchivos2 {
    void leerBinario(String directorio, String archivo) {
        File f = new File(directorio + "/" + archivo);
        try (Scanner lector = new Scanner(f)) {
            while (lector.hasNextByte())
                System.out.println("%X" + lector.nextByte());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}