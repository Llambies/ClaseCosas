package com.adrian.tema01;

import com.adrian.tema01.boletin01.*;
import com.adrian.tema01.mapeoManual.Mapeador;

public class Main {

    static String path = "./src/main/java/com/adrian/tema01/boletin01/";

    public static void main(String[] args) {
        Mapeador<String,Integer> mapeador = new Mapeador();

        mapeador.agregar("hhh",1);
        mapeador.agregar("b",2);
        mapeador.agregar("hhg",2);

        System.out.println(mapeador.buscar("hhg"));
        System.out.println(mapeador.buscar("hdg"));


    }

    void ejecutarEjercicios(String[] args) {
        new Ej01().main(args);
        new Ej02().main(args);
        new Ej03().main(args);
        new Ej04().main(args);
        new Ej05().main(args);
        new Ej06().main(args);
        new Ej07().main(args);
        String[] argsEj08 = new String[1];
        argsEj08[0] = path + "Ej04.java";
        new Ej08().main(argsEj08);
        String[] argsEj09 = new String[2];
        argsEj09[0] = path + "Ej04.java";
        argsEj09[1] = path + "Ej05.java";
        new Ej09().main(argsEj09);
        new Ej10().main(argsEj09);
        new Ej11().main(argsEj09);
    }
}