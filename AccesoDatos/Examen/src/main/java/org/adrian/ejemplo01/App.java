package org.adrian.ejemplo01;

import java.util.Scanner;

import org.adrian.ejemplo01.DAOs.EquipoDAOImplJDBC;
import org.adrian.ejemplo01.DAOs.PartidoDAOImplJSON;
import org.adrian.ejemplo01.connection.DataSource;
import org.adrian.ejemplo01.repository.LigaRepository;

public class App {
    static LigaRepository repository;
    static DataSource dataSource;

    public static void main(String[] args) {

        dataSource = new DataSource();
        // 1. Creamos un repositorio
        repository = new LigaRepository(new EquipoDAOImplJDBC(dataSource),
                new PartidoDAOImplJSON(new EquipoDAOImplJDBC(dataSource)));

        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("-------------------------");
            System.out.println("LA LIGA");
            System.out.println("-------------------------");
            System.out.println("1. Mostrar equipos");
            System.out.println("2. Mostrar resultados");
            System.out.println("-------------------------");
            System.out.println("0. Salir");
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
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }

        sc.close();
    }

    static void Ej01() {
        String equipos = repository.getEquipos();
        System.out.println(equipos);
    }

    static void Ej02() {
        String resultados = repository.getResultados();
        System.out.println(resultados);
    }

}
