package com.adrian.tema01.boletin02;

import java.io.*;

public class Ej05 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 5");
        TresEnRaya t = GameStorage.cargarEstado();
        System.out.println("Turno actual: " + t.getTurnoActual());
        t.jugar(0, 0);
        t.congelar();
    }
}

class TresEnRaya implements Serializable {

    private final int[][] tablero;
    private int turnoActual;

    public TresEnRaya() {
        tablero = new int[3][3];
        turnoActual = 0;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public boolean jugar(int fila, int columna) {
        if (esPosicionValida(fila, columna) && tablero[fila][columna] == 0) {
            tablero[fila][columna] = turnoActual + 1;
            turnoActual = (turnoActual + 1) % 2;
            return true;
        }
        return false;
    }

    public void congelar() {
        GameStorage.guardarEstado(this);
    }

    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < 3 && columna >= 0 && columna < 3;
    }
}

class GameStorage {
    private static final String PATH_ARCHIVO = "./src/main/java/com/adrian/tema01/boletin02/estado.tres";

    static void guardarEstado(TresEnRaya juego) {
        try (FileOutputStream fos = new FileOutputStream(PATH_ARCHIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(juego);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static TresEnRaya cargarEstado() {
        File archivo = new File(PATH_ARCHIVO);
        if (!archivo.exists()) {
            return new TresEnRaya();
        }
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (TresEnRaya) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new TresEnRaya();
        }
    }
}