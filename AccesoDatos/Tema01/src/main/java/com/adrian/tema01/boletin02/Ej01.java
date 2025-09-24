package com.adrian.tema01.boletin02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Haz un metodo que reciba como parámetro un fichero con 20 DNI aleatorios (sin letra) y:
 * a) compruebe que todos los DNI tienen una longitud de 8 dígitos, sino es así, deberá rellenar con 0’s por la izquierda.
 * b) calcule la letra correspondiente a cada DNI y la añada por la derecha.
 * c) Guarde el resultado en otro archivo cuyo nombre sea el resultado de concatenar el nombre del archivo
 * original más “_conLetras” en la parte del nombre.
 */
public class Ej01 {


    public static void main(String[] args) {
        DNIFileFormater.format("./src/main/java/com/adrian/tema01/boletin02/dni.txt");
    }

}

class DNIFileFormater {

    public static void format(String path) {
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String dni = sc.nextLine();
                String dniFormated = rellenadorNumeros(dni);
                content.append(dniFormated);
                content.append(calculadorLetra(Integer.parseInt(dni)));
                content.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.substring(0, path.lastIndexOf(".")) + "_conLetras" + path.substring(path.lastIndexOf(".")), true))) {
            writer.write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String rellenadorNumeros(String dni) {
        if (dni.length() > 8) {
            return dni.substring(0, 8);
        } else if (dni.length() < 8) {
            return String.format("%08d", Integer.parseInt(dni));
        }
        return dni;
    }

    static char calculadorLetra(int dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letras.charAt(dni % 23);
    }
}
