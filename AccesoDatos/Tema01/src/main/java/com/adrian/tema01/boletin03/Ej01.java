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
        try {
            File inputFile = new File("./src/main/java/com/adrian/tema01/boletin03/datasets/empleados.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("empleado");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String id = eElement.getAttribute("id");
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                    String salario = eElement.getElementsByTagName("salario").item(0).getTextContent();

                    System.out.println("ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Salario: " + salario);
                    System.out.println("--------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
