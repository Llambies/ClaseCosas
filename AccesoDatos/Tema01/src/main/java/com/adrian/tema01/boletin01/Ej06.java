package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Scanner;

public class Ej06 {

    public static void main(String[] args) {
        GestionArchivos2 ga = new GestionArchivos2();
        ga.leerArchivo("./src/main/java/com/adrian/tema01/boletin01", "Ej06.java");

    }
}

class GestionArchivos2 extends GestionArchivos {
    void leerArchivo(String directorio, String archivo){
        File f = new File(directorio + "/" + archivo);

        try (Scanner lector = new Scanner(f)){
            while ( lector.hasNextLine())
                System.out.println(lector.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}