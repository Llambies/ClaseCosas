package com.adrian.tema01.boletin03;

import java.io.File;
import java.nio.file.Files;
import com.google.gson.Gson;

/**
 * Cargar el dataset inventario.json y mostrar la ubicación del producto
 * indicado por el usuario.
 * 
 */
public class Ej07 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/inventario.json";
    static Almacen almacen;

    public static void main(String[] args) {
        try {
            File inputFile = new File(PATH);
            String content = new String(Files.readAllBytes(inputFile.toPath()));
            almacen = new Gson().fromJson(content, Almacen.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Ejercicio 7");
            System.out.println("\n");
            System.out.println("Introduce el id del producto: ");
            String id = System.console().readLine();
            System.out.println("Producto: " + almacen.getProducto(id).nombre + " - Ubicación: " + almacen.getProducto(id).ubicacion.pasillo + " - " + almacen.getProducto(id).ubicacion.estante);
        }
    }
}

class Almacen {
    String almacen;
    String actualizado;
    Producto[] productos;

    public Almacen(String almacen, String actualizado, Producto[] productos) {
        this.almacen = almacen;
        this.actualizado = actualizado;
        this.productos = productos;
    }

    public Producto getProducto(String id) {
        for (Producto producto : this.productos) {
            if (producto.id.equals(id)) {
                return producto;
            }
        }
        return null;
    }
}

class Producto {
    String id;
    String nombre;
    int stock;
    double precio;
    String[] tags;
    Ubicacion ubicacion;

    public Producto(String id, String nombre, int stock, double precio, String[] tags, Ubicacion ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.tags = tags;
        this.ubicacion = ubicacion;
    }

}

class Ubicacion {
    int pasillo;
    String estante;

    public Ubicacion(int pasillo, String estante) {
        this.pasillo = pasillo;
        this.estante = estante;
    }
}
