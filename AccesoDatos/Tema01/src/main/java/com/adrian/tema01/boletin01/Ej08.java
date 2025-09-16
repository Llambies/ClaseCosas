package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Scanner;

public class Ej08 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 8");
        String path = args[0];
        try (Scanner sc = new Scanner(new File(path))){
            while ( sc.hasNextLine())
                System.out.println(sc.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
