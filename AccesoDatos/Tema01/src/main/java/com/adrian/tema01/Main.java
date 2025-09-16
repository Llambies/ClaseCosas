package com.adrian.tema01;

import com.adrian.tema01.boletin01.*;

public class Main {
    public static void main(String[] args) {
        new Ej01().main(args);
        new Ej02().main(args);
        new Ej03().main(args);
        new Ej04().main(args);
        new Ej05().main(args);
        new Ej06().main(args);
        new Ej07().main(args);
        String[] argsEj08 = new String[1];
        argsEj08[0] = "./src/main/java/com/adrian/tema01/boletin01/Ej04.java";
        new Ej08().main(argsEj08);
        String[] argsEj09 = new String[2];
        argsEj09[0] = "./src/main/java/com/adrian/tema01/boletin01/Ej04.java";
        argsEj09[1] = "./src/main/java/com/adrian/tema01/boletin01/Ej05.java";
        new Ej09().main(argsEj09);
        new Ej10().main(argsEj09);
        new Ej11().main(argsEj09);
    }
}