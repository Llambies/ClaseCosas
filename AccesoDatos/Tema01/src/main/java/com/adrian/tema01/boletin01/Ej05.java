package com.adrian.tema01.boletin01;

import java.io.File;
import java.util.Arrays;

public class Ej05 {

    public static void main(String[] args) {
        GestionArchivos ga = new GestionArchivos();
        ga.CrearArchivo("./src/main/java/com/adrian/tema01/boletin01", "Prueba.txt");
        ga.listarDirectorio("./src/main/java/com/adrian/tema01/boletin01");
        ga.verInfo("./src/main/java/com/adrian/tema01/boletin01", "Prueba.txt");

    }


}


class GestionArchivos {
    public boolean CrearArchivo(String directorio, String archivo) {
        try {
            File f = new File(directorio + "/" + archivo);
            return f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    void listarDirectorio(String directorio){
        File f = new File(directorio);
        System.out.println(Arrays.toString(f.list()));

    }

    void verInfo(String directorio, String archivo){
        File f = new File(directorio + "/" + archivo);
        System.out.println("Nombre: " + f.getName());
        System.out.println("Ruta absoluta: " + f.getAbsolutePath());
        System.out.println("Leer?: " + f.canRead());
        System.out.println("Escribir?: " + f.canWrite());
    }
}