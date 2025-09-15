package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Arrays;

public class Ej03 {
    public static void main(String[] args) {
        // Comprobar la carpeta
        File f = new File("./src/main/java/com/adrian/tema01/boletin01");
        System.out.println("Nombre: " + f.getName());
        System.out.println("Ruta absoluta: " + f.getAbsolutePath());
        System.out.println("Leer?: " + f.canRead());
        System.out.println("Escribir?: " + f.canWrite());
    }
}
