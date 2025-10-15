package com.adrian.tema01.boletin02;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Ej04 {
    static GestionDeAccesoJSON gda = null;
    static String usuarioLogeado = "";

    public static void main(String[] args) {
        System.out.println("Ejercicio 4");
        try {
            gda = new GestionDeAccesoJSON("./src/main/java/com/adrian/tema01/boletin02/login.json");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (gda != null) {
            inicio();
        }
    }

    public static void inicio() {
        System.out.println("***************************");
        System.out.println("***  1. Validar acceso  ***");
        System.out.println("***************************");

        int opcion = 0;
        opcion = new java.util.Scanner(System.in).nextInt();
        switch (opcion) {
            case 1:
                validarAcceso();
                break;
            case 2:
                System.out.println("Fin del programa");
                break;
            default:
                System.out.println("Opcion no valida");
                inicio();
                break;
        }
    }

    public static void validarAcceso() {
        System.out.println("Ingrese su nombre de usuario:");
        String usuario = new java.util.Scanner(System.in).nextLine();
        System.out.println("Ingrese su clave:");
        String password = new java.util.Scanner(System.in).nextLine();
        if (gda.validarAcceso(usuario, password)) {
            System.out.println("Acceso autorizado");
            usuarioLogeado = usuario.trim();
            menuPrincipal();
        } else {
            System.out.println("Acceso denegado");
        }
    }

    public static void menuPrincipal() {
        System.out.println("***************************");
        System.out.println("* 1. Modificar contraseña *");
        System.out.println("* 2. Salir                *");
        System.out.println("***************************");

        int opcion = 0;
        opcion = new java.util.Scanner(System.in).nextInt();
        switch (opcion) {
            case 1:
                cambiarContra();
                break;
            case 2:
                System.out.println("Fin del programa");
                break;
            default:
                System.out.println("Opcion no valida");
                inicio();
                break;
        }
    }

    public static void cambiarContra() {
        System.out.println("Ingrese su clave actual:");
        String password = new java.util.Scanner(System.in).nextLine();
        if (gda.validarAcceso(usuarioLogeado, password)) {
            nuevaContra();
        } else {
            System.out.println("Acceso denegado");
            cambiarContra();
        }


    }

    public static void nuevaContra() {
        System.out.println("Ingrese su nueva clave:");
        System.out.println("Debe tener al menos 8 caracteres, con al menos una mayuscula, una minuscula, un numero y un caracter especial");
        String newPassword = new java.util.Scanner(System.in).nextLine();
        try {
            gda.validarEstructuraContra(newPassword);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            nuevaContra();
        }
        try {
            gda.cambiarContra(usuarioLogeado, newPassword);
            System.out.println("Clave cambiada correctamente");
            menuPrincipal();
        } catch (NullPointerException e) {
            System.out.println("No existe el usuario: " + usuarioLogeado);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}

class GestionDeAccesoJSON {
    File archivoDatos;
    Map<String, String> usuarios;
    MessageDigest md;

    public GestionDeAccesoJSON(String path) throws NoSuchAlgorithmException, IOException {
        archivoDatos = new File(path);
        Gson gson = new Gson();

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Error al crear el algoritmo: " + e.getMessage());
        }

        // Cargar los datos si existen, sino crear
        if (!archivoDatos.exists()) {
            usuarios = new HashMap<>();
            // Agregar un usuario por defecto
            usuarios.put("admin", encrypt("S3cret@"));
            guardarUsuarios();
        } else {
            try (FileReader fr = new FileReader(archivoDatos)) {
                usuarios = gson.fromJson(fr, new TypeToken<Map<String, String>>() {
                }.getType());
                if (usuarios == null) {
                    usuarios = new HashMap<>();
                }
            }
        }

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Error al crear el algoritmo: " + e.getMessage());
        }
    }

    // Metodo para guardar los datos en JSON
    private void guardarUsuarios() throws IOException {
        Gson gson = new Gson();
        try (FileWriter fw = new FileWriter(archivoDatos)) {
            gson.toJson(usuarios, fw);
        }
    }

    // Validar acceso por usuario y contraseña
    public boolean validarAcceso(String usuario, String password) {
        String hashAlmacenado = usuarios.get(usuario);
        if (hashAlmacenado == null) return false;
        return encrypt(password).equals(hashAlmacenado);
    }

    // Cambiar la contraseña de un usuario
    public void cambiarContra(String usuario, String nuevaPassword) throws NullPointerException, IOException {

        usuarios.put(usuario, encrypt(nuevaPassword));
        guardarUsuarios();

    }

    // Metodo para validar estructura
    public void validarEstructuraContra(String nuevaContra) throws Exception {
        if (nuevaContra.length() < 8) {
            throw new Exception("La clave debe tener al menos 8 caracteres");
        } else if (!nuevaContra.matches(".*[A-Z].*")) {
            throw new Exception("La clave debe tener al menos una mayuscula");
        } else if (!nuevaContra.matches(".*[a-z].*")) {
            throw new Exception("La clave debe tener al menos una minuscula");
        } else if (!nuevaContra.matches(".*[0-9].*")) {
            throw new Exception("La clave debe tener al menos un numero");
        } else if (!nuevaContra.matches(".*[^a-zA-Z0-9].*")) {
            throw new Exception("La clave debe tener al menos un caracter especial");
        }
    }

    // Encriptar contraseña
    public String encrypt(String password) {
        byte[] input = password.getBytes(StandardCharsets.UTF_8);
        byte[] hash = md.digest(input);
        return Base64.getEncoder().encodeToString(hash);
    }
}
