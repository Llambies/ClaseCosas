package com.adrian.tema01.boletin01;

import java.io.File;

public class Ej01 {
    public static void main(String[] args) {
        // Comprobar la carpeta
        File f = new File("./src/main/java/com/adrian/tema01/boletin01");
        System.out.println(f.isDirectory());
    }
}
