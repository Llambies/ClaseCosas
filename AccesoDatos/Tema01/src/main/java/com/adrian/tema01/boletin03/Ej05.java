package com.adrian.tema01.boletin03;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cargar el dataset alumnos.json y mostrar el nombre y la fecha de nacimiento
 * de los alumnos.
 */
public class Ej05 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/alumnos.json";

    public static void main(String[] args) {
        try {
            File inputFile = new File(PATH);
            String content = new String(Files.readAllBytes(inputFile.toPath()));
            JSONTokener tokener = new JSONTokener(content);

            ArrayList<Alumno> alumnosArray = parserAlumnos(tokener);
            System.out.println("Ejercicio 5");
            for (Alumno alumno : alumnosArray) {
                System.out.println("Nombre: " + alumno.nombre + " - Fecha de nacimiento: " + alumno.fechaNacimiento);
            }
            System.out.println("\n");
            System.out.println("Ejercicio 6");
            for (Alumno alumno : alumnosArray) {
                System.out.println("Nombre: " + alumno.nombre + " - Nota mas alta: " + Arrays.stream(alumno.notas)
                        .max((nota1, nota2) -> Double.compare(nota1.nota, nota2.nota)).get().nota);
            }
            System.out.println("\n");
            double maxNotaMedia = 0;
            Alumno alumnoNotaMediaMasAlta = null;
            for (Alumno alumno : alumnosArray) {
                double notaMedia = Arrays.stream(alumno.notas).mapToDouble(nota -> nota.nota).average().orElse(0);
                if (notaMedia > maxNotaMedia) {
                    maxNotaMedia = notaMedia;
                    alumnoNotaMediaMasAlta = alumno;
                }
            }
            System.out.println(
                    "Nombre: " + alumnoNotaMediaMasAlta.nombre + " - con la nota media mas alta: " + maxNotaMedia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Alumno> parserAlumnos(JSONTokener tokener) {
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        JSONArray alumnosArray = new JSONArray(tokener);
        for (int i = 0; i < alumnosArray.length(); i++) {
            JSONObject alumno = alumnosArray.getJSONObject(i);
            String id = alumno.getString("id");
            String nombre = alumno.getString("nombre");
            boolean matriculado = alumno.getBoolean("matriculado");
            String fechaNacimiento = alumno.getString("fechaNacimiento");
            Nota[] notas = new Nota[alumno.getJSONArray("notas").length()];
            for (int j = 0; j < notas.length; j++) {
                JSONObject nota = alumno.getJSONArray("notas").getJSONObject(j);
                notas[j] = new Nota(nota.getString("asignatura"), nota.getDouble("nota"));
            }
            alumnos.add(new Alumno(id, nombre, matriculado, fechaNacimiento, notas));
        }
        return alumnos;
    }
}

class Alumno {
    String id;
    String nombre;
    boolean matriculado;
    String fechaNacimiento;
    Nota[] notas;

    public Alumno(String id, String nombre, boolean matriculado, String fechaNacimiento, Nota[] notas) {
        this.id = id;
        this.nombre = nombre;
        this.matriculado = matriculado;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = notas;
    }
}

class Nota {
    String asignatura;
    double nota;

    public Nota(String asignatura, double nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }
}