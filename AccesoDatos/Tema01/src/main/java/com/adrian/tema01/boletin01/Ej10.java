package com.adrian.tema01.boletin01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ej10 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 10");
        if (args.length < 1) {
            System.out.println("No se ha proporcionado parametros");
            return;
        } else if (args.length > 2) {
            System.out.println("Se ha proporcionado más de dos parametros");
            return;
        } else if (args.length < 2) {
            System.out.println("Se ha proporcionado solo un parametro");
            return;
        }

        String archivo1 = args[0];
        File f1 = new File(archivo1);
        if (!f1.exists()) {
            System.out.println("El primer archivo no existe");
            return;
        }
        String archivo2 = args[1];
        File f2 = new File(archivo2);
        if (!f2.exists()) {
            System.out.println("El segundo archivo no existe");
            return;
        }
        StringBuilder contenido = new StringBuilder();
        try (Scanner sc1 = new Scanner(f1)) {
            while (sc1.hasNextLine()) {
                contenido.append(sc1.nextLine());
                contenido.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (Scanner sc2 = new Scanner(f2)) {
            while (sc2.hasNextLine()) {
                contenido.append(sc2.nextLine());
                contenido.append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Ej10.txt", true));
            writer.write(contenido.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
