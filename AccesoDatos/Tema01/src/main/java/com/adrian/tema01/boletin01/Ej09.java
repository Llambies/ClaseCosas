package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Scanner;

public class Ej09 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 9");
        String archivo1 = args[0];
        String archivo2 = args[1];

        File f1 = new File(archivo1);
        File f2 = new File(archivo2);
        if (!f1.exists() || !f2.exists()) {
            System.out.println("Archivo no encontrado");
            return;
        }

        try (Scanner sc1 = new Scanner(f1)) {
            try (Scanner sc2 = new Scanner(f2)) {
                boolean coinciden = true;
                while (sc1.hasNextLine() && sc2.hasNextLine()) {
                    String linea1 = sc1.nextLine();
                    String linea2 = sc2.nextLine();
                    if (!linea1.equals(linea2)) {
                        coinciden = false;
                        break;
                    }

                }
                System.out.println("Coinciden los archivos: " + archivo1 + " y " + archivo2 + "? " + coinciden);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
