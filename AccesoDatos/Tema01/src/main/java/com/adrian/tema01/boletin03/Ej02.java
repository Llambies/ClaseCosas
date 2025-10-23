package com.adrian.tema01.boletin03;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Ej02 {

    static String PATH = "./src/main/java/com/adrian/tema01/boletin03/datasets/empleados.xml";

    public static void main(String[] args) {
        GestorEmpleados ge = new GestorEmpleados(PATH);
        System.out.println(ge.salarioMedioporDepartamento());

    }
}

/**
 * Gestor de empleados y departamentos que carga datos desde XML y calcula
 * salario medio por departamento.
 */
class GestorEmpleados {
    ArrayList<Empleado> empleados;
    ArrayList<String> departamentos;

    GestorEmpleados(String pathArchivo) {
        empleados = new ArrayList<>();
        departamentos = new ArrayList<String>() {
        };
        if (pathArchivo == null || pathArchivo.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacía");
        }

        try {
            File inputFile = new File(pathArchivo);
            if (!inputFile.exists()) {
                throw new IllegalStateException("El archivo no existe: " + pathArchivo);
            }
            if (!inputFile.canRead()) {
                throw new SecurityException("No se puede leer el archivo: " + pathArchivo);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("empleado");
            if (nList.getLength() == 0) {
                System.out.println("Advertencia: No se encontraron empleados en el archivo");
                return;
            }

            for (int i = 0; i < nList.getLength(); i++) {
                try {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() != Node.ELEMENT_NODE)
                        continue;

                    Element eElement = (Element) nNode;

                    String id = eElement.getAttribute("id");
                    String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent().trim();
                    String departamento = eElement.getElementsByTagName("departamento").item(0).getTextContent().trim();

                    NodeList salarioNodes = eElement.getElementsByTagName("salario");
                    if (salarioNodes.getLength() == 0) {
                        throw new IllegalStateException("No se encontró el nodo de salario para el empleado: " + id);
                    }

                    double salario = Double.parseDouble(salarioNodes.item(0).getTextContent().trim());
                    String fechaAlta = eElement.getElementsByTagName("fechaAlta").item(0).getTextContent().trim();

                    Node monedaNode = salarioNodes.item(0).getAttributes().getNamedItem("moneda");
                    if (monedaNode == null) {
                        throw new IllegalStateException(
                                "No se encontró el atributo 'moneda' para el salario del empleado: " + id);
                    }
                    String moneda = monedaNode.getTextContent();

                    Empleado empleado = new Empleado(id, nombre, departamento, moneda, salario, fechaAlta);
                    agregarEmpleado(empleado);

                } catch (Exception e) {
                    System.err.println("Error al procesar el empleado #" + (i + 1) + ": " + e.getMessage());
                    // Continuar con el siguiente empleado
                }
            }
        } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
            System.err.println("Error de configuración: " + e.getMessage());
            throw e; // Relanzar excepciones críticas
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            System.err.println("Error en la configuración del parser XML: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.err.println("Error al parsear el archivo XML: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al cargar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Agrega un empleado a la lista de empleados y actualiza la lista de
     * departamentos si es necesario.
     * 
     * @param empleado El empleado a agregar
     * @throws IllegalArgumentException si el empleado es nulo o no tiene
     *                                  departamento
     */
    void agregarEmpleado(Empleado empleado) {
        try {
            if (empleado == null) {
                throw new IllegalArgumentException("El empleado no puede ser nulo");
            }
            if (empleado.departamento == null || empleado.departamento.trim().isEmpty()) {
                throw new IllegalArgumentException("El departamento del empleado no puede estar vacío");
            }

            empleados.add(empleado);
            if (!departamentos.contains(empleado.departamento)) {
                departamentos.add(empleado.departamento);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
            throw e; // Relanzamos para que el llamador pueda manejarlo si es necesario
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Calcula el salario medio por departamento.
     * 
     * @return Un String con el salario medio por departamento.
     *         |Departamento | Nº Empleados | Salario medio|
     * @throws IllegalStateException si no hay departamentos o empleados
     */
    String salarioMedioporDepartamento() throws IllegalStateException {
        try {
            if (departamentos == null || departamentos.isEmpty()) {
                throw new IllegalStateException("No hay departamentos disponibles");
            }
            if (empleados == null || empleados.isEmpty()) {
                throw new IllegalStateException("No hay empleados registrados");
            }

            StringBuilder salida = new StringBuilder();
            salida.append("Departamento | Nº Empleados | Salario medio\n");
            salida.append("-------------------------------------------\n");

            for (String departamento : departamentos) {
                try {
                    double salarioTotal = 0;
                    int nEmpleados = 0;

                    for (Empleado empleado : empleados) {
                        if (empleado != null && empleado.departamento != null &&
                                empleado.departamento.equals(departamento)) {
                            salarioTotal += empleado.salario;
                            nEmpleados++;
                        }
                    }

                    if (nEmpleados > 0) {
                        double salarioMedio = salarioTotal / nEmpleados;
                        salida.append(String.format("%-12s | %-13d | %.2f\n",
                                departamento, nEmpleados, salarioMedio));
                    } else {
                        salida.append(String.format("%-12s | %-13s | %s\n",
                                departamento, "0", "N/A (sin empleados)"));
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar el departamento " + departamento + ": " + e.getMessage());
                    salida.append(String.format("%-12s | %-13s | %s\n",
                            departamento, "ERROR", "Error en el cálculo"));
                }
            }
            return salida.toString();

        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error en salarioMedioporDepartamento: " + e.getMessage());
        } 
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