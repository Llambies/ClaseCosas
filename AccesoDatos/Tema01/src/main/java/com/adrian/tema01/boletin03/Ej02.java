package com.adrian.tema01.boletin03;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ej02 {

    static String PATH = "./src/main/java/com/adrian/tema01/boletin03/datasets/empleados.xml";

    public static void main(String[] args) {
        GestorEmpleados ge = new GestorEmpleados(PATH);
        System.out.println(ge.salarioMedioporDepartamento());

    }
}

class GestorEmpleados {
    ArrayList<Empleado> empleados;
    ArrayList<String> departamentos;

    GestorEmpleados(String pathArchivo) {
        empleados =new ArrayList<>();
        departamentos = new ArrayList<String>() {
        };
        try {
            File inputFile = new File(pathArchivo);
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
                    String departamento = eElement.getElementsByTagName("departamento").item(0).getTextContent();
                    double salario = Double.parseDouble(eElement.getElementsByTagName("salario").item(0).getTextContent());
                    String fechaAlta = eElement.getElementsByTagName("fechaAlta").item(0).getTextContent();
                    String moneda = eElement.getElementsByTagName("salario").item(0).getAttributes().getNamedItem("moneda").getTextContent();

                    Empleado empleado = new Empleado(id, nombre, departamento, moneda, salario, fechaAlta);
                    agregarEmpleado(empleado);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        if (!departamentos.contains(empleado.departamento)) {
            departamentos.add(empleado.departamento);
        }
    }

    String salarioMedioporDepartamento() {
        StringBuilder salida = new StringBuilder();
        salida.append("Departamento | Nº Empleados | Salario medio\n");
        for (String departamento : departamentos) {
            double salarioTotal = 0;
            int nEmpleados = 0;
            for (Empleado empleado : empleados) {
                if (empleado.departamento.equals(departamento)) {
                    salarioTotal += empleado.salario;
                    nEmpleados++;
                }
            }

            salida.append(departamento).append(" | ").append(nEmpleados).append(" | ").append(salarioTotal / nEmpleados).append("\n");
        }
        return salida.toString();
    }
}

class Empleado {
    String id;
    String nombre;
    String departamento;
    String moneda;
    double salario;
    String fechaAlta;

    public Empleado(String id, String nombre, String departamento, String moneda, double salario, String fechaAlta) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.moneda = moneda;
        this.salario = salario;
        this.fechaAlta = fechaAlta;
    }
}