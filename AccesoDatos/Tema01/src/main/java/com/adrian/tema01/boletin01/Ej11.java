package com.adrian.tema01.boletin01;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Ej11 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 11");
        String archivo1 = args[0];
        File f1 = new File(archivo1);
        String archivo2 = args[1];
        File f2 = new File(archivo2);
        StringBuilder contenido = new StringBuilder();

        try (Scanner sc1 = new Scanner(f1)) {
            try (Scanner sc2 = new Scanner(f2)) {
                while (sc1.hasNextLine() && sc2.hasNextLine()) {
                    String linea1 = sc1.nextLine();
                    String linea2 = sc2.nextLine();
                    contenido.append(linea1 + linea2);
                    contenido.append("\n");
                }

                while (sc1.hasNextLine()) {
                    contenido.append(sc1.nextLine());
                    contenido.append("\n");
                }

                while (sc2.hasNextLine()) {
                    contenido.append(sc2.nextLine());
                    contenido.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Ej11.txt", true));
            writer.write(contenido.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
