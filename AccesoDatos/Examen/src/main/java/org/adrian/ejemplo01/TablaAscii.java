package org.adrian.ejemplo01;


import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para formatear datos tabulares como una tabla ASCII.
 */
public class TablaAscii {

    /** Ancho de cada columna después del cálculo. */
    private final int[] anchosColumnas;

    /** Datos de la tabla (lista de filas). Cada fila es un array de String. */
    private final List<String[]> filas = new ArrayList<>();

    /**
     * Constructor que recibe la primera fila (usualmente los encabezados).
     *
     * @param encabezado La fila de encabezado.
     */
    public TablaAscii(String... encabezado) {
        // Guardamos la primera fila y calculamos los anchos iniciales.
        filas.add(encabezado);
        anchosColumnas = new int[encabezado.length];
        for (int i = 0; i < encabezado.length; i++) {
            anchosColumnas[i] = encabezado[i] != null ? encabezado[i].length() : 0;
        }
    }

    /**
     * Añade una nueva fila a la tabla.
     *
     * @param fila Los valores de la fila.
     * @throws IllegalArgumentException si el número de columnas no coincide.
     */
    public void agregarFila(String... fila) {
        if (fila.length != anchosColumnas.length) {
            throw new IllegalArgumentException(
                "Número de columnas incorrecto. Se esperaban "
                + anchosColumnas.length + " pero recibí " + fila.length);
        }
        filas.add(fila);
        // Actualizamos los anchos máximos.
        for (int i = 0; i < fila.length; i++) {
            int largo = fila[i] != null ? fila[i].length() : 0;
            if (largo > anchosColumnas[i]) {
                anchosColumnas[i] = largo;
            }
        }
    }

    /**
     * Genera la representación en texto de la tabla.
     *
     * @return Cadena con la tabla formateada.
     */
    public String renderizar() {
        StringBuilder sb = new StringBuilder();

        // Línea superior
        sb.append(generarSeparador()).append('\n');

        // Cada fila
        for (int idx = 0; idx < filas.size(); idx++) {
            String[] fila = filas.get(idx);
            sb.append('|');
            for (int col = 0; col < fila.length; col++) {
                String celda = fila[col] != null ? fila[col] : "";
                sb.append(' ')
                  .append(padDerecha(celda, anchosColumnas[col]))
                  .append(' ');
                sb.append('|');
            }
            sb.append('\n');

            // Después del encabezado o de cada fila añadimos una línea separadora
            sb.append(generarSeparador()).append('\n');
        }

        return sb.toString();
    }

    /** Construye una línea como +----+------+----+ */
    private String generarSeparador() {
        StringBuilder sep = new StringBuilder();
        sep.append('+');
        for (int ancho : anchosColumnas) {
            sep.append("-".repeat(ancho + 2)).append('+');
        }
        return sep.toString();
    }

    /** Rellena a la derecha con espacios hasta alcanzar el ancho deseado. */
    private String padDerecha(String texto, int ancho) {
        if (texto.length() >= ancho) {
            return texto;
        }
        return texto + " ".repeat(ancho - texto.length());
    }

    // ------------------- Ejemplo de uso -------------------
    public static void main(String[] args) {
        TablaAscii tabla = new TablaAscii("ID", "Nombre", "País");

        tabla.agregarFila("1", "Ana García", "España");
        tabla.agregarFila("2", "John Doe", "Estados Unidos");
        tabla.agregarFila("3", "Li Wei", "China");

        System.out.println(tabla.renderizar());
    }
}