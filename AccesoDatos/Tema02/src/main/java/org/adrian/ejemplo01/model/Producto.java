package org.adrian.ejemplo01.model;

import java.util.Objects;

public class Producto {
    private final String id;
    private final String nombre;
    private final String descripcion;
    private final double precio;
    private final int stock;

    public Producto(String nombre, String descripcion, double precio, int stock) {
        this.id = Producto.generadorDeUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(String id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    private static synchronized String generadorDeUUID() {
        return "Prod" + System.currentTimeMillis() + Math.random();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Producto producto = (Producto) obj;
        return Objects.equals(this.id, producto.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.id);
    }
}
