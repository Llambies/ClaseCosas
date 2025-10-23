package com.adrian.tema01.boletin03;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Ej03 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/biblioteca.xml";

    public static void main(String[] args) {
        List<Libro> libros = new ArrayList<>();

        File inputFile = new File(PATH);
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.err.println("Error: No se puede leer el archivo " + PATH);
            return;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("libro");
            if (nList.getLength() == 0) {
                System.out.println("No se encontraron libros en el archivo.");
                return;
            }

            for (int i = 0; i < nList.getLength(); i++) {
                try {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        String isbn = eElement.hasAttribute("isbn") ? eElement.getAttribute("isbn")
                                : "ISBN_DESCONOCIDO";

                        String titulo = "";
                        try {
                            titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
                        } catch (Exception e) {
                            System.err.println("Advertencia: Libro sin título, se omitirá");
                            continue;
                        }

                        int anio = 0;
                        try {
                            anio = Integer.parseInt(eElement.getElementsByTagName("anio").item(0).getTextContent());
                        } catch (NumberFormatException | NullPointerException e) {
                            System.err.println("Advertencia: Año inválido para el libro: " + titulo);
                            anio = 0; // Default value
                        }

                        ArrayList<String> generos = new ArrayList<>();
                        try {
                            Element elementGeneros = (Element) eElement.getElementsByTagName("generos").item(0);
                            if (elementGeneros != null) {
                                for (int j = 0; j < elementGeneros.getElementsByTagName("genero").getLength(); j++) {
                                    String genero = elementGeneros.getElementsByTagName("genero").item(j)
                                            .getTextContent();
                                    if (genero != null && !genero.trim().isEmpty()) {
                                        generos.add(genero.trim());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Advertencia: Error al leer géneros para el libro: " + titulo);
                        }

                        boolean disponible = false;
                        try {
                            String dispText = eElement.getElementsByTagName("disponible").item(0).getTextContent();
                            disponible = Boolean.parseBoolean(dispText);
                        } catch (Exception e) {
                            System.err
                                    .println("Advertencia: Estado de disponibilidad inválido para el libro: " + titulo);
                        }

                        Autor autor = null;
                        try {
                            Element elementAutor = (Element) eElement.getElementsByTagName("autor").item(0);
                            if (elementAutor != null) {
                                String nombreAutor = elementAutor.getElementsByTagName("nombre").item(0)
                                        .getTextContent();
                                String nacimientoAutor = elementAutor.getElementsByTagName("nacimiento").item(0)
                                        .getTextContent();
                                if (nombreAutor != null && !nombreAutor.trim().isEmpty()) {
                                    autor = new Autor(nombreAutor.trim(),
                                            nacimientoAutor != null ? nacimientoAutor.trim() : "");
                                }
                            }

                            if (autor == null) {
                                autor = new Autor("Autor Desconocido", "");
                                System.err.println(
                                        "Advertencia: Información de autor no encontrada para el libro: " + titulo);
                            }

                            Libro libro = new Libro(isbn, titulo, autor, anio, generos, disponible);
                            libros.add(libro);

                        } catch (Exception e) {
                            System.err.println("Error procesando el autor del libro: " + titulo);
                            if (titulo != null && !titulo.trim().isEmpty()) {
                                autor = new Autor("Autor Desconocido", "");
                                libros.add(new Libro(isbn, titulo, autor, anio, generos, disponible));
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error procesando el libro en la posición " + i + ": " + e.getMessage());
                }
            }

        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.err.println("Error de configuración del parser XML: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.err.println("Error al analizar el archivo XML: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(
                "Nombres de los libros: " + libros.stream().map(libro -> libro.titulo).collect(Collectors.toList()));
        HashMap<String, Integer> generos = new HashMap<>();
        for (Libro libro : libros) {
            for (String genero : libro.generos) {
                generos.put(genero, generos.getOrDefault(genero, 0) + 1);
            }
        }
        System.out.println("Libros por genero: " + generos);
    }
}

class Libro {
    String ISBN;
    String titulo;
    Autor autor;
    int anio;
    ArrayList<String> generos;
    boolean disponible;

    public Libro(String ISBN, String titulo, Autor autor, int anio, ArrayList<String> generos, boolean disponible) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.generos = generos;
        this.disponible = disponible;
    }
}

class Autor {
    String nombre;
    String nacimiento;

    public Autor(String nombre, String nacimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
    }
}