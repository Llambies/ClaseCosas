package org.adrian.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utilidad para el manejo seguro de contraseñas usando PBKDF2.
 * PBKDF2 (Password-Based Key Derivation Function 2) es una función
 * de derivación de claves recomendada para almacenar contraseñas de forma segura.
 */
public class PasswordUtil {
    private static final int ITERATIONS = 65536; // Número de iteraciones (más alto = más seguro pero más lento)
    private static final int KEY_LENGTH = 256; // Longitud de la clave en bits
    private static final int SALT_LENGTH = 16; // Longitud del salt en bytes
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Genera un hash seguro de una contraseña usando PBKDF2.
     * El resultado incluye el salt y el hash codificados en Base64.
     * 
     * @param password La contraseña en texto plano
     * @return String con formato "salt:hash" codificado en Base64
     */
    public static String hashPassword(String password) {
        try {
            // Generar un salt aleatorio
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Crear la especificación de clave PBKDF2
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

            // Generar el hash
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Codificar salt y hash en Base64 y combinarlos
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error al generar hash de contraseña", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con un hash almacenado.
     * 
     * @param password La contraseña en texto plano a verificar
     * @param storedHash El hash almacenado con formato "salt:hash"
     * @return true si la contraseña coincide, false en caso contrario
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Separar salt y hash del string almacenado
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);

            // Crear la especificación de clave PBKDF2 con el mismo salt
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);

            // Generar el hash de la contraseña proporcionada
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Comparar los hashes de forma segura (evita timing attacks)
            return constantTimeEquals(hash, storedHashBytes);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Compara dos arrays de bytes de forma constante en tiempo
     * para evitar ataques de timing.
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
