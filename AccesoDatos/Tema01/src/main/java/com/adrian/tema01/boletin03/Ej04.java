package com.adrian.tema01.boletin03;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ej04 {
    static String PATH = "src/main/java/com/adrian/tema01/boletin03/datasets/pedidos.xml";

    public static void main(String[] args) {
        GestorPedidos gestorPedidos = new GestorPedidos(PATH);
        System.out.println("Pedidos: " + gestorPedidos.pedidos);
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("1. Mostrar un pedido");
            System.out.println("2. Recalcular totales");
            System.out.println("4. Salir");
            opcion = Integer.parseInt(System.console().readLine());
            switch (opcion) {
                case 1:
                    System.out.println("Introduce el id del pedido: ");
                    String id = System.console().readLine();
                    System.out.println("Pedido: " + gestorPedidos.getPedido(id));
                case 2:
                    System.out.println(gestorPedidos.recalcularTotales());
                case 4:
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }
}

class GestorPedidos {
    String path;
    ArrayList<Pedido> pedidos;

    public GestorPedidos(String path) {
        this.path = path;
        this.pedidos = new ArrayList<>();

        try {
            File inputFile = new File(this.path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("pedido");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getAttribute("id");
                    String fecha = eElement.getElementsByTagName("fecha").item(0).getTextContent();
                    Element elementCliente = (Element) eElement.getElementsByTagName("cliente").item(0);
                    String nombre = elementCliente.getElementsByTagName("nombre").item(0).getTextContent();
                    String email = elementCliente.getElementsByTagName("email").item(0).getTextContent();
                    Cliente cliente = new Cliente(nombre, email);
                    Element elementItems = (Element) eElement.getElementsByTagName("items").item(0);
                    NodeList nListItems = elementItems.getElementsByTagName("item");
                    ArrayList<Item> items = new ArrayList<>();
                    for (int j = 0; j < nListItems.getLength(); j++) {
                        Node nNodeItem = nListItems.item(j);
                        if (nNodeItem.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElementItem = (Element) nNodeItem;
                            String sku = eElementItem.getAttribute("sku");
                            String descripcion = eElementItem.getElementsByTagName("descripcion").item(0)
                                    .getTextContent();
                            int cantidad = Integer
                                    .parseInt(eElementItem.getElementsByTagName("cantidad").item(0).getTextContent());
                            double precioUnitario = Double.parseDouble(
                                    eElementItem.getElementsByTagName("precioUnitario").item(0).getTextContent());
                            Item item = new Item(sku, descripcion, cantidad, precioUnitario);
                            items.add(item);
                        }
                    }
                    double total = Double.parseDouble(eElement.getElementsByTagName("total").item(0).getTextContent());
                    Pedido pedido = new Pedido(id, fecha, cliente, items, total);
                    this.pedidos.add(pedido);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pedido getPedido(String id) {
        return this.pedidos.stream().filter(pedido -> pedido.id.equals(id)).findFirst().orElse(null);
    }

    public String recalcularTotales() {
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : pedidos) {
            sb.append("Pedido: " + pedido.id + "\n");
            float total = 0;
            for (Item item : pedido.items) {
                total += item.precioUnitario * item.cantidad;
            }
            sb.append("Total calculado: " + total + "\n");
            sb.append("Total: " + pedido.total + "\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}

class Pedido {
    String id;
    String fecha;
    Cliente cliente;
    ArrayList<Item> items;
    double total;

    Pedido(String id, String fecha, Cliente cliente, ArrayList<Item> items, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = items;
        this.total = total;
    }
}

class Cliente {
    String nombre;
    String email;

    Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
}

class Item {
    String sku;
    String descripcion;
    int cantidad;
    double precioUnitario;

    Item(String sku, String descripcion, int cantidad, double precioUnitario) {
        this.sku = sku;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
}