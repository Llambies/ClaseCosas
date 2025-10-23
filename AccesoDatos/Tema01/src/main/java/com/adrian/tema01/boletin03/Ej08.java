package com.adrian.tema01.boletin03;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Ej08 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/peliculas.json";
    static ArrayList<Pelicula> peliculas;

    public static void main(String[] args) {
        try {
            File inputFile = new File(PATH);
            String content = new String(Files.readAllBytes(inputFile.toPath()));
            JSONTokener tokener = new JSONTokener(content);
            peliculas = parserPeliculas(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Puntuacion media por pelicula");
        for (Pelicula pelicula : peliculas) {
            System.out.println(pelicula.titulo + " - " +
                    (pelicula.puntuaciones.values().stream().reduce(0.0,
                            (subtotal, puntuacion) -> subtotal + puntuacion) / pelicula.puntuaciones.size()));
        }

        System.out.println("\n");
        ArrayList<String> generos = new ArrayList<String>();
        for (Pelicula pelicula : peliculas) {
            for (String genero : pelicula.generos) {
                if (!generos.contains(genero)) {
                    generos.add(genero);
                }
            }
        }
        System.out.println("Generos: " + generos);

    }

    static ArrayList<Pelicula> parserPeliculas(JSONTokener tokener) {
        JSONObject peliculasJson = new JSONObject(tokener);
        JSONArray peliculasArray = peliculasJson.getJSONArray("peliculas");
        ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
        for (int i = 0; i < peliculasArray.length(); i++) {
            Pelicula pelicula = parserPelicula(peliculasArray.getJSONObject(i));
            peliculas.add(pelicula);
        }
        return peliculas;
    }

    static Pelicula parserPelicula(JSONObject pelicula) {
        String id = pelicula.getString("id");
        String titulo = pelicula.getString("titulo");
        String director = pelicula.getString("director");
        int estreno = pelicula.getInt("estreno");
        int duracionMin = pelicula.getInt("duracionMin");
        ArrayList<String> generos = new ArrayList<String>();
        for (int i = 0; i < pelicula.getJSONArray("generos").length(); i++) {
            generos.add(pelicula.getJSONArray("generos").getString(i));
        }
        HashMap<String, Double> puntuaciones = new HashMap<String, Double>();
        puntuaciones.put("imdb", pelicula.getJSONObject("puntuaciones").getDouble("imdb"));
        puntuaciones.put("rt", (pelicula.getJSONObject("puntuaciones").getDouble("rt") / 10));
        return new Pelicula(id, titulo, director, estreno, duracionMin, generos, puntuaciones);
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