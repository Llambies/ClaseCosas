package com.adrian.tema01.boletin02;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

public class Ej03 {
    static GestionDeAcceso gda = null;

    public static void main(String[] args) {
        System.out.println("Ejercicio 3");
        try {
            gda = new GestionDeAcceso("./src/main/java/com/adrian/tema01/boletin02/login.properties");
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
        System.out.println("Ingrese su clave:");
        String password = new java.util.Scanner(System.in).nextLine();
        if (gda.validarAcceso(password)) {
            System.out.println("Acceso autorizado");
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
        if (gda.validarAcceso(password)) {
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
        gda.cambiarContra(newPassword);
        System.out.println("Clave cambiada correctamente");
        menuPrincipal();
    }
}

/**
 * La clase GestionDeAcceso gestiona el acceso de usuarios mediante la validación de contraseñas.
 * Esta funcionalidad incluye encriptación de contraseñas, validación de estructura de contraseñas
 * y el manejo de un archivo de propiedades para almacenar información relevante.
 */
class GestionDeAcceso {
    File archivoDatos;
    Properties propiedades;

    MessageDigest md;

    /**
     * Constructor de la clase GestionDeAcceso que inicializa los atributos, gestiona la creación
     * del archivo de propiedades si no existe, y crea una clave predeterminada en caso de archivos vacíos.
     * También inicializa el algoritmo de encriptación SHA-1 para el manejo de contraseñas.
     *
     * @param path Ruta del archivo de propiedades que será utilizado para almacenar las claves de acceso.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo de encriptación SHA-1.
     * @throws Exception                Si ocurre un problema al crear o escribir en el archivo de propiedades.
     */
    public GestionDeAcceso(String path) throws NoSuchAlgorithmException, Exception {
        File f = new File(path);
        propiedades = new Properties();

        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Error al crear el algoritmo: " + e.getMessage());
        }

        if (!f.exists()) {
            try {
                if (!f.createNewFile()) {
                    throw new Exception("No se pudo crear el archivo");
                }
            } catch (IOException e) {
                throw new Exception("Error al crear archivo: " + e.getMessage());
            }
        }
        propiedades.load(new FileInputStream(f));
        archivoDatos = f;

        if (propiedades.isEmpty()) {
            String clave = encrypt("S3cret@");
            propiedades.setProperty("clave", clave);
            propiedades.store(new FileOutputStream(f), null);
        }
    }

    /**
     * Valida el acceso comparando el hash de la contraseña ingresada con la clave almacenada en las propiedades.
     *
     * @param password La contraseña ingresada por el usuario que se desea validar.
     * @return true si la contraseña ingresada coincide con la clave almacenada, false en caso contrario.
     */
    public boolean validarAcceso(String password) {
        return encrypt(password).equals(propiedades.getProperty("clave"));
    }

    /**
     * Cambia la contraseña almacenada en el archivo de propiedades.
     * Encripta la nueva contraseña proporcionada y actualiza el archivo de datos correspondiente.
     * En caso de error, imprime un mensaje indicando el problema.
     *
     * @param nuevaPassword La nueva contraseña que será encriptada y almacenada en el archivo de datos.
     */
    public void cambiarContra(String nuevaPassword) {
        try {
            propiedades.setProperty("clave", encrypt(nuevaPassword));
            propiedades.store(new FileOutputStream(archivoDatos), null);
        } catch (Exception e) {
            System.out.println("Error al cambiar la clave: " + e.getMessage());
        }
    }

    /**
     * Valida que la nueva contraseña cumpla con ciertos requisitos de complejidad.
     * La contraseña debe tener al menos 8 caracteres, incluir al menos una letra mayúscula,
     * una letra minúscula, un número y un carácter especial.
     *
     * @param nuevaContra La nueva contraseña que será validada.
     * @throws Exception Si la contraseña no cumple con alguno de los requisitos especificados.
     */
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

    /**
     * Encrypts the provided password using the SHA-1 algorithm and encodes the resulting hash in Base64 format.
     *
     * @param password the plaintext password to be encrypted
     * @return the encrypted password in Base64 encoded format
     */
    String encrypt(String password) {
        byte[] input = password.getBytes(StandardCharsets.UTF_8);
        byte[] hash = md.digest(input);
        return Base64.getEncoder().encodeToString(hash);
    }
}