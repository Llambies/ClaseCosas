package com.adrian.tema01.boletin02;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.text.SimpleDateFormat;

public class Ej02 {
    public static void main(String[] args) {
        Alumno alumno1 = new Alumno(1234, "Pepe", "Marin", "Roig", new Date());
        Alumno alumno2 = new Alumno(1235, "Juan", "Gomez", "Martines", new Date());

        GestionAlumnos gestionAlumnos = new GestionAlumnos("../src/main/java/com/adrian/tema01/boletin02/alumnos.txt");


    }
}

class Alumno {
    final int NIA;
    final String nombre;
    final String apellido1;
    final String apellido2;
    final Date fechaNacimiento;

    public Alumno(int NIA, String nombre, String apellido1, String apellido2, Date fechaNacimiento) {
        this.NIA = NIA;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getNIA() {
        return NIA;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
}

class GestionAlumnos {
    String pathArchivo;
    ArrayList<Alumno> alumnos;

    public GestionAlumnos(String pathArchivo) {
        this.pathArchivo = pathArchivo;
        alumnos = new ArrayList<Alumno>();
        File file = new File(pathArchivo);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Leer archivo y almacenar en array
            try (Scanner scanner = new Scanner(file)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                while (scanner.hasNextLine()) {
                    String[] datos = scanner.nextLine().split(",");
                    alumnos.add(new Alumno(
                            Integer.parseInt(datos[0]),
                            datos[1],
                            datos[2],
                            datos[3],
                            dateFormat.parse(datos[4])
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertaAlumnos(Alumno[] alumnos) {
        this.alumnos.addAll(List.of(alumnos));
        guardarArchivo();
    }

    public void eliminarAlumno(Alumno alumno) {
        this.alumnos.remove(alumno);
        guardarArchivo();
    }

    void guardarArchivo() {
        File file = new File(pathArchivo);
        if (!file.exists()) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Alumno alumno : alumnos) {

            stringBuilder.append(alumno.getNIA());
            stringBuilder.append(",");
            stringBuilder.append(alumno.getNombre());
            stringBuilder.append(",");
            stringBuilder.append(alumno.getApellido1());
            stringBuilder.append(",");
            stringBuilder.append(alumno.getApellido2());
            stringBuilder.append(",");
            stringBuilder.append(alumno.getFechaNacimiento().toString());
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(alumnos.toArray());
    }

}