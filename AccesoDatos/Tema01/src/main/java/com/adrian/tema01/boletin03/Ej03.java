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
        try {
            File inputFile = new File(PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("libro");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String isbn = eElement.getAttribute("isbn");
                    String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
                    int anio = Integer.parseInt(eElement.getElementsByTagName("anio").item(0).getTextContent());
                    ArrayList<String> generos = new ArrayList<>();
                    Element elementGeneros = (Element) eElement.getElementsByTagName("generos").item(0);
                    for (int j = 0; j < elementGeneros.getElementsByTagName("genero").getLength(); j++) {
                        generos.add(elementGeneros.getElementsByTagName("genero").item(j).getTextContent());
                    }
                    boolean disponible = Boolean.parseBoolean(eElement.getElementsByTagName("disponible").item(0).getTextContent());
                    Element elementAutor = (Element) eElement.getElementsByTagName("autor").item(0);
                    String nombreAutor = elementAutor.getElementsByTagName("nombre").item(0).getTextContent();
                    String nacimientoAutor = elementAutor.getElementsByTagName("nacimiento").item(0).getTextContent();
                    
                    Autor autor = new Autor(nombreAutor, nacimientoAutor);
                    Libro libro = new Libro(isbn, titulo, autor, anio, generos, disponible);
                    libros.add(libro);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Nombres de los libros: " + libros.stream().map(libro -> libro.titulo).collect(Collectors.toList()));
        HashMap<String, Integer> generos = new HashMap<>();
        for (Libro libro : libros) {
            for (String genero : libro.generos) {
                generos.put(genero, generos.getOrDefault(genero, 0) + 1);
            }
        }
        System.out.println("Libros por genero: " + generos);
    }
}

class Libro{
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

class Autor{
    String nombre;
    String nacimiento;

    public Autor(String nombre, String nacimiento) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
    }
}