package com.adrian.tema01.boletin03;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Ej01 {
    public static void main(String[] args) {
        File inputFile = new File("./src/main/java/com/adrian/tema01/boletin03/datasets/empleados.xml");

        if (!inputFile.exists()) {
            System.err.println(
                    "Error: El archivo no se encuentra en la ruta especificada: " + inputFile.getAbsolutePath());
            return;
        }

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("empleado");

            if (nList.getLength() == 0) {
                System.out.println("No se encontraron empleados en el archivo.");
                return;
            }

            for (int i = 0; i < nList.getLength(); i++) {
                try {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;

                        String id = eElement.hasAttribute("id") ? eElement.getAttribute("id") : "No especificado";

                        String nombre = "No disponible";
                        String salario = "No disponible";

                        try {
                            Node nombreNode = eElement.getElementsByTagName("nombre").item(0);
                            if (nombreNode != null) {
                                nombre = nombreNode.getTextContent().trim();
                            }

                            Node salarioNode = eElement.getElementsByTagName("salario").item(0);
                            if (salarioNode != null) {
                                salario = salarioNode.getTextContent().trim();
                            }
                        } catch (Exception e) {
                            System.err.println("Error al procesar los datos de un empleado: " + e.getMessage());
                            continue;
                        }

                        System.out.println("ID: " + id);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Salario: " + salario);
                        System.out.println("--------------------");
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar el empleado en la posición " + i + ": " + e.getMessage());
                }
            }
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.err.println("Error en la configuración del parser XML: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.err.println("Error al parsear el archivo XML: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
