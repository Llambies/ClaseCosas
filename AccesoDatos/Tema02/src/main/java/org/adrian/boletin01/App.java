package org.adrian.boletin01;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.List;
import java.util.Scanner;

public class App {

    static Analista analista;

    public static void main(String[] args) {
        try {
            analista = new Analista();
        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("Menu");
            System.out.println("0. Salir");
            System.out.println("1. Listar equipos");
            System.out.println("2. Ciclistas por equipo");
            System.out.println("3. Listar etapas");
            System.out.println("4. Resumen etapas");
            String entrada = sc.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un numero");
                continue;
            }

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo");
                    break;

                case 1:
                    Ej01();
                    break;
                case 2:
                    Ej02();
                    break;
                case 3:
                    // Ej03b();
                    break;
                case 4:
                    // Ej03c();
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }

        sc.close();
    }

    static void Ej01() {
        TablaAscii tabla = new TablaAscii("ID", "Nombre", "País");
        for (Equipo equipo : analista.obtenerEquipos()) {
            tabla.agregarFila(String.valueOf(equipo.getId_equipo()), equipo.getNombre(), equipo.getPais());
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej02() {
        TablaAscii tabla = new TablaAscii("ID", "Nombre", "País");
        for (Equipo equipo : analista.obtenerEquipos()) {
            tabla.agregarFila(String.valueOf(equipo.getId_equipo()), equipo.getNombre(), equipo.getPais());
        }
        System.out.println(tabla.renderizar());
        System.out.println("Selecciona un equipo");
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        try {
            int id_equipo = Integer.parseInt(entrada);
            List<Ciclista> ciclistas = analista.obtenerCiclistasPorEquipo(id_equipo);
            TablaAscii tabla2 = new TablaAscii("ID", "Nombre", "País");
            for (Ciclista ciclista : ciclistas) {
                tabla2.agregarFila(String.valueOf(ciclista.getId_ciclista()), ciclista.getNombre(), ciclista.getPais());
            }
            System.out.println(tabla2.renderizar());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, introduce un numero");
        } finally {
            sc.close();
        }
    }
}
