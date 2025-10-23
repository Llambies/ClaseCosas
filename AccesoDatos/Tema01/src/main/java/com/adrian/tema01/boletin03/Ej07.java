package com.adrian.tema01.boletin03;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
            almacen = parserAlmacen(new JSONTokener(content));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Ejercicio 7");
            System.out.println("\n");
            System.out.println("Introduce el id del producto: ");
            String id = System.console().readLine();
            System.out.println("Producto: " + almacen.getProducto(id).nombre + " - Ubicación: "
                    + almacen.getProducto(id).ubicacion.pasillo + " - " + almacen.getProducto(id).ubicacion.estante);
        }
    }

    static Almacen parserAlmacen(JSONTokener tokener) {
        JSONObject almacenJson = new JSONObject(tokener);
        String almacen = almacenJson.getString("almacen");
        String actualizado = almacenJson.getString("actualizado");
        ArrayList<Producto> productos = parserProductos(almacenJson.getJSONArray("productos"));
        return new Almacen(almacen, actualizado, productos.toArray(new Producto[productos.size()]));
    }

    static ArrayList<Producto> parserProductos(JSONArray productosArray) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        for (int i = 0; i < productosArray.length(); i++) {
            JSONObject producto = productosArray.getJSONObject(i);
            String id = producto.getString("id");
            String nombre = producto.getString("nombre");
            int stock = producto.getInt("stock");
            double precio = producto.getDouble("precio");
            String[] tags = producto.getJSONArray("tags").toString().split(",");
            JSONObject ubicacion = producto.getJSONObject("ubicacion");
            int pasillo = ubicacion.getInt("pasillo");
            String estante = ubicacion.getString("estante");
            productos.add(new Producto(id, nombre, stock, precio, tags, new Ubicacion(pasillo, estante)));
        }
        return productos;
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
