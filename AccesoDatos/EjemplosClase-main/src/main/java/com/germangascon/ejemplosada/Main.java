package com.germangascon.ejemplosada;

import com.germangascon.ejemplosada.hashmap.Alumno;
import com.germangascon.ejemplosada.hashmap.MyHashMap;
import com.germangascon.ejemplosada.lib.Bombo;
import com.germangascon.ejemplosada.properties.MyProperties;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * ${NAME}
 * License: 🅮 Public Domain
 * Created on: 2025-09-11
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Main {
    private static MyHashMap<String, Alumno> alumnos;

    public static void main(String[] args) {
        // new MyProperties();

        /*
        alumnos = new MyHashMap<>(20);
        crearAlumnosAleatorios(10);
        System.out.println(alumnos.values());
         */
    }

    private static void crearAlumnosAleatorios(int cantidad) {
        Faker faker = new Faker(Locale.of("ES"));
        Bombo bombo = new Bombo(1000, 9_999_999);
        for (int i = 0; i < cantidad; i++) {
            // String nia = String.format("%08d", bombo.getBola());
            String nia = String.format("%08d", i);
            String nombre = faker.name().firstName();
            String apellido1 = faker.name().lastName();
            String apellido2 = faker.name().lastName();
            LocalDate fechaNacimiento = faker.timeAndDate().birthday(18, 50);
            alumnos.put(nia, new Alumno(nia, nombre, apellido1, apellido2, fechaNacimiento));
        }

        System.out.println(alumnos.get("00000003"));
    }
}

