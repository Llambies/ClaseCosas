package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Arrays;

public class Ej02   {
    public static void main(String[] args) {
        // Comprobar la carpeta
        File f = new File("./src/main/java/com/adrian/tema01/boletin01");
        System.out.println(Arrays.toString(f.list()));
    }
}
