package com.adrian.tema01.boletin03;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class Ej08 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/peliculas.json";
    static ArrayList<Pelicula> peliculas;

    public static void main(String[] args) {
        try {
            File inputFile = new File(PATH);
            String content = new String(Files.readAllBytes(inputFile.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Ejercicio 7");
            System.out.println("\n");
            System.out.println("Introduce el id del producto: ");
            String id = System.console().readLine();
            System.out.println("Producto: " + almacen.getProducto(id).nombre + " - Ubicación: "
                    + almacen.getProducto(id).ubicacion.pasillo + " - " + almacen.getProducto(id).ubicacion.estante);
        }
    }
}

class Pelicula {
    String id;
    String titulo;
    String director;
    int estreno;
    int duracionMin;
    ArrayList<String> generos;
    HashMap<String, Double> puntuaciones;

    public Pelicula(String id, String titulo, String director, int estreno, int duracionMin, ArrayList<String> generos,
            HashMap<String, Double> puntuaciones) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.estreno = estreno;
        this.duracionMin = duracionMin;
        this.generos = generos;
        this.puntuaciones = puntuaciones;
    }
}