package org.adrian.boletin01;

import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Scanner;

public class App {

    static BaseDeDatos bd;

    public static void main(String[] args) {

        try {
            bd = new BaseDeDatos();
            bd.conectar();
        } catch (SQLTimeoutException e) {
            System.out.println("Timeout al conectar a la base de datos: " + e.getMessage());
            return;
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
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
            System.out.println("4. Resumen por tipo de etapa");
            System.out.println("5. Velocidad media de un ciclista");
            System.out.println("6. Clasificacion por etapa");
            System.out.println("7. Clasificación de la montaña");
            System.out.println("8. Clasificación regularidad");
            System.out.println("9. Clasificación general");
            System.out.println("10. Clasificación por equipos");
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
                    Ej03b();
                    break;
                case 4:
                    Ej03c();
                    break;
                case 5:
                    Ej04();
                    break;
                case 6:
                    Ej05();
                    break;
                case 7:
                    Ej06();
                    break;
                case 8:
                    Ej07();
                    break;
                case 9:
                    Ej08();
                    break;
                case 10:
                    Ej09();
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

        try {
            for (String[] equipo : bd.obtenerEquipos()) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej02() {
        TablaAscii tabla = new TablaAscii("ID", "Nombre", "País");

        try {
            for (String[] equipo : bd.obtenerEquipos()) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());

        System.out.println("Introduce el ID del equipo");
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        int id = Integer.parseInt(entrada);

        try {
            for (String[] equipo : bd.obtenerCiclistasPorEquipo(id)) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
            return;
        } finally {
            sc.close();
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej03b() {
        TablaAscii tabla = new TablaAscii("ID", "Id Carrera", "Num Etapa", "Fecha", "Salida", "Llegada",
                "Distancia", "Tipo");
        try {
            for (String[] equipo : bd.obtenerEtapas()) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2], equipo[3], equipo[4], equipo[5],
                        equipo[6], equipo[7]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej03c() {
        TablaAscii tabla = new TablaAscii("Tipo etapa", "Cantidad", "Distancia total");

        try {
            for (String[] equipo : bd.resumenPorTipoEtapa()) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej04() {

        TablaAscii tablaCiclistas = new TablaAscii("ID", "Ciclista");

        try {
            for (String[] equipo : bd.obtenerCiclistas()) {
                tablaCiclistas.agregarFila(String.valueOf(equipo[0]), equipo[1]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        }
        System.out.println(tablaCiclistas.renderizar());

        System.out.println("Introduce el ID del ciclista");
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        int id = Integer.parseInt(entrada);

        TablaAscii tabla = new TablaAscii("ID", "Velocidad media");
        try {
            String[] dato = bd.velocidadMediaPorCiclista(id);
            tabla.agregarFila(String.valueOf(dato[0]), dato[1]);
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        } finally {
            sc.close();
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej05() {

        TablaAscii tablaEtapas = new TablaAscii("ID", "Carrera", "Num Etapa", "Fecha", "Salida", "Llegada",
                "Distancia", "Tipo");

        try {
            for (String[] equipo : bd.obtenerEtapas()) {
                tablaEtapas.agregarFila(String.valueOf(equipo[0]), equipo[1], equipo[2], equipo[3], equipo[4],
                        equipo[5], equipo[6], equipo[7]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        }
        System.out.println(tablaEtapas.renderizar());

        System.out.println("Introduce el ID de la etapa");
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();
        int id = Integer.parseInt(entrada);

        TablaAscii tabla = new TablaAscii("ID Ciclista", "Nombre", "Equipo", "Tiempo");
        try {
            for (String[] dato : bd.clasificacionPorEtapa(id)) {
                tabla.agregarFila(dato[0], dato[1], dato[2], dato[3]);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener etapas: " + e.getMessage());
            return;
        } finally {
            sc.close();
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej06() {
        TablaAscii tabla = new TablaAscii("Posición", "Ciclista", "Equipo", "Puntos");

        try {
            for (String[] ciclista : bd.clasificacionCiclistasEnMontaña()) {
                tabla.agregarFila(String.valueOf(ciclista[0]), ciclista[1], ciclista[2], String.valueOf(ciclista[3]));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clasificación de ciclistas en montaña: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej07() {
        TablaAscii tabla = new TablaAscii("Posición", "Ciclista", "Equipo", "Tiempo");

        try {
            for (String[] ciclista : bd.clasificacionRegularidad()) {
                tabla.agregarFila(String.valueOf(ciclista[0]), ciclista[1], ciclista[2], String.valueOf(ciclista[3]));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clasificación de ciclistas en regularidad: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej08() {
        TablaAscii tabla = new TablaAscii("Posición", "Ciclista", "Equipo", "Tiempo");

        try {
            for (String[] ciclista : bd.clasificacionGeneral()) {
                tabla.agregarFila(String.valueOf(ciclista[0]), ciclista[1], ciclista[2], String.valueOf(ciclista[3]));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clasificación general: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }

    static void Ej09() {
        TablaAscii tabla = new TablaAscii("Posición", "Equipo", "Tiempo");

        try {
            for (String[] equipo : bd.clasificacionPorEquipos()) {
                tabla.agregarFila(String.valueOf(equipo[0]), equipo[1], String.valueOf(equipo[2]));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener clasificación por equipos: " + e.getMessage());
            return;
        }
        System.out.println(tabla.renderizar());
    }
}
