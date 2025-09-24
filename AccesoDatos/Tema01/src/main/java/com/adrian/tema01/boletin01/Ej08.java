package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Scanner;

public class Ej08 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 8");
        if (args.length <= 0) {
            System.out.println("No se ha proporcionado un parametro");
            return;
        } else if (args.length > 1) {
            System.out.println("Se ha proporcionado más de un parametro");
            return;
        }
        String path = args[0];
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine())
                System.out.println(sc.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
