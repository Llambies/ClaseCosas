package org.adrian;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Concierto concierto = null;

    public static void main(String[] args) {
        int opcion = -1;
        Scanner scanner = new Scanner(System.in);

        while (opcion != 0) {
            System.out.println("| ------------------------------------ |");
            System.out.println("| -------------MENU------------------- |");
            System.out.println("| 1. Configurar concierto              |");
            System.out.println("| 2. Mostrar informacion del concierto |");
            System.out.println("| 3. Iniciar simulacion                |");
            System.out.println("| 0. Salir                             |");
            System.out.println("| ------------------------------------ |");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Ingrese un numero valido");
                scanner.nextLine(); // Clear invalid input
                continue;
            }

            switch (opcion) {
                case 1:
                    concierto = configurarConcierto(scanner);
                    continue;
                case 2:
                    mostrarInformacionConcierto(concierto);
                    continue;
                case 3:
                    iniciarSimulacion();
                    continue;
                case 0:
                    break;
                default:
                    System.out.println("❌ Opcion no valida");
                    continue;
            }
        }
        scanner.close();
    }

    public static Concierto configurarConcierto(Scanner scanner) {

        scanner.nextLine(); // Clear buffer
        String nombre = "";
        while (nombre.isBlank()) {
            System.out.println("Ingrese el nombre del concierto: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isBlank()) {
                System.out.println("❌ Error: El nombre no puede estar vacio");
            }
        }
        int totalEntradas = 0;
        while (totalEntradas <= 0) {
            System.out.println("Ingrese el total de entradas: ");
            try {
                totalEntradas = scanner.nextInt();
                if (totalEntradas <= 0) {
                    System.out.println("❌ Error: El numero de entradas debe ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Introduce un numero entero valido");
                scanner.nextLine(); // Clear invalid input
            }
        }
        double precioEntrada = 0;
        while (precioEntrada <= 0) {
            System.out.println("Ingrese el precio de la entrada: ");
            try {
                precioEntrada = scanner.nextDouble();
                if (precioEntrada <= 0) {
                    System.out.println("❌ Error: El precio debe ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Introduce un precio valido (use punto decimal)");
                scanner.nextLine(); // Clear invalid input
            }
        }
        int comprasConcurrentes = 0;
        while (comprasConcurrentes <= 0) {
            System.out.println("Ingrese el numero de compras concurrentes: ");
            try {
                comprasConcurrentes = scanner.nextInt();
                if (comprasConcurrentes <= 0) {
                    System.out.println("❌ Error: El numero de compras debe ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Introduce un numero entero valido");
                scanner.nextLine(); // Clear invalid input
            }
        }
        Concierto concierto = new Concierto(nombre, totalEntradas, precioEntrada, comprasConcurrentes);
        System.out.println("Concierto configurado exitosamente");
        return concierto;
    }

    public static void mostrarInformacionConcierto(Concierto concierto) {
        if (concierto == null) {
            System.out.println(
                    "❌ Error: No hay ningun concierto configurado. Por favor, configure un concierto primero.");
            return;
        }
        System.out.println("Nombre: " + concierto.getNombre());
        System.out.println("Total de entradas: " + concierto.getTotalEntradas());
        System.out.println("Precio de la entrada: " + concierto.getPrecioEntrada());
        System.out.println("Numero de compras concurrentes: " + concierto.getComprasConcurrentes());
    }

    public static void iniciarSimulacion() {
        for (int i = 0; i < 1000; i++) {
            Thread fan = new Thread(new Fan(concierto));
            fan.start();
        }
    }
}