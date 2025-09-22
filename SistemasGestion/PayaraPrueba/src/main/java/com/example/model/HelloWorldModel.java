package com.example.model;  

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo para la aplicación Hello World
 * Representa los datos que se mostrarán en la vista
 */
public class HelloWorldModel {

    private String nombre;
    private String fechaHora;

    // Constructor
    public HelloWorldModel(String nombre) {
        this.nombre = nombre;
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.fechaHora = ahora.format(formatter);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    // Setters (opcionales, pero útiles)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
