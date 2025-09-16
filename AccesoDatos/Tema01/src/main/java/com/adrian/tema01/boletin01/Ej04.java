package com.adrian.tema01.boletin01;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Ej04 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 4");
        File f = new File("./src/main/java/com/adrian/tema01/boletin01/Ej04.java");
        System.out.println("Nombre: " + f.getName());
        System.out.println("Ruta absoluta: " + f.getAbsolutePath());
        System.out.println("Oculto?: " + f.isHidden());
        System.out.println("Leer?: " + f.canRead());
        System.out.println("Escribir?: " + f.canWrite());

        long milliseconds = f.lastModified();
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(milliseconds / 1000, 0, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
        String formattedDate = dateTime.format(formatter);

        System.out.println("Ult Mod: " + formattedDate);

        f.setLastModified(12);
        milliseconds = f.lastModified();
        dateTime = LocalDateTime.ofEpochSecond(milliseconds / 1000, 0, ZoneOffset.UTC);
        formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
        formattedDate = dateTime.format(formatter);

        System.out.println("Ult Mod: " + formattedDate);
        System.out.println("Size en bytes: " + f.length());
        System.out.println("Size en KB: " + f.length() / 1024.0);
        System.out.println("Size en MB: " + f.length() / 1024.0 / 1024);

    }
}
