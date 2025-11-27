package com.adrian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import com.adrian.model.Coche;
import com.adrian.model.EscuchadorParkings;
import com.adrian.model.Parking;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class Controlador implements EscuchadorParkings {

    @FXML
    TextField fieldNombre;
    @FXML
    TextField fieldCoches;
    @FXML
    TextField fieldPlazas;
    @FXML
    TextField fieldPlazasElectricas;
    @FXML
    TextField fieldPlazasEspeciales;
    @FXML
    TextField fieldPrecio;
    @FXML
    TextField fieldPrecioElectrico;
    @FXML
    TextField fieldPrecioEspecial;
    @FXML
    TextField fieldAccesos;
    @FXML
    Label labelEstado;
    @FXML
    Label labelRecaudacion;
    @FXML
    Label labelPlazas;
    @FXML
    Label labelPlazasElectricas;
    @FXML
    Label labelPlazasEspeciales;
    @FXML
    Label labelCochesEntrando;
    @FXML
    Label labelCochesSaliendo;
    @FXML
    Button botonVerHistoral;

    @FXML
    private void guardarConfiguracion() throws IOException {

        String nombre = fieldNombre.getText();
        if (nombre.isEmpty()) {
            aviso(
                    AlertType.ERROR,
                    "Nombre invalidas",
                    "El nombre no puede estar vacio",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        int coches;
        try {
            coches = Integer.parseInt(fieldCoches.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Coches invalidos",
                    "Introduce un numero de coches valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        int plazas;
        try {
            plazas = Integer.parseInt(fieldPlazas.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Plazas invalidas",
                    "Introduce un numero de plazas valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        int plazasElectricas;
        try {
            plazasElectricas = Integer.parseInt(fieldPlazasElectricas.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Plazas electricas invalidas",
                    "Introduce un numero de plazas electricas valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        int plazasEspeciales;
        try {
            plazasEspeciales = Integer.parseInt(fieldPlazasEspeciales.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Plazas especiales invalidas",
                    "Introduce un numero de plazas especiales valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(fieldPrecio.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Precio invalido",
                    "Introduce un precio valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        double precioElectrico;
        try {
            precioElectrico = Double.parseDouble(fieldPrecioElectrico.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Precio electrico invalido",
                    "Introduce un precio para electricos valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        double precioEspecial;
        try {
            precioEspecial = Double.parseDouble(fieldPrecioEspecial.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Precio especial invalido",
                    "Introduce un precio para especiales valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        int accesos;
        try {
            accesos = Integer.parseInt(fieldAccesos.getText());
        } catch (Exception e) {
            aviso(
                    AlertType.ERROR,
                    "Accesos invalidas",
                    "Introduce un numero de accesos valido",
                    "Error!",
                    Color.color(0.8, 0.1, 0.1));
            return;
        }

        Parking p = new Parking(nombre, coches, plazas, plazasElectricas, plazasEspeciales, precio, precioElectrico,
                precioEspecial, accesos);
        App.guardarConfiguracion(p);
        actualizarUI(p);
        aviso(AlertType.INFORMATION, "Configurado correctamente",
                "El parking ha sido configurado y guardado correctamente", "Guardado", Color.color(0.1, 0.8, 0.1));

    }

    @FXML
    private void actualizarUI(Parking p) {
        cambioRecaudacion(0);

        cambioPlazas(0, p.totalPlazas);
        cambioPlazasElectricas(0, p.totalPlazasElectricas);
        cambioPlazasEspeciales(0, p.totalPlazasEspeciales);

        cambioCochesEntrando(new ArrayList<>());
        cambioCochesSaliendo(new ArrayList<>());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        // App.abrirVentana("secondary");
        try {
            App.iniciarSimulacion(this);
        } catch (Exception e) {
            aviso(AlertType.ERROR, "Error al iniciar simulacion", e.getMessage(), "Error", Color.color(0.8, 0.1, 0.1));
        }
    }

    @FXML
    private void abrirHistorial() throws IOException {
        App.abrirVentana("secondary");
    }

    public void callback() {
        System.out.println("Holi");
    }

    void aviso(AlertType tipo, String titulo, String cuerpo, String estado, Color colorEstado) {
        Alert alert;
        alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(cuerpo);
        alert.showAndWait();
        labelEstado.setText(estado);
        labelEstado.setTextFill(colorEstado);
    }

    @Override
    public void cambioRecaudacion(double value) {
        labelRecaudacion.setText(String.format("%.2f$", value));
    }

    @Override
    public void cambioPlazas(int ocupadas, int totales) {
        labelPlazas.setText(ocupadas + "/" + totales);
    }

    @Override
    public void acaboUnCoche(int acabados, int totales) {
        if (acabados == totales) {
            botonVerHistoral.setVisible(true);
            ;
        }
    }

    @Override
    public void cambioPlazasElectricas(int ocupadas, int totales) {
        labelPlazasElectricas.setText(ocupadas + "/" + totales);
    }

    @Override
    public void cambioPlazasEspeciales(int ocupadas, int totales) {
        labelPlazasEspeciales.setText(ocupadas + "/" + totales);
    }

    @Override
    public void cambioCochesEntrando(List<Coche> coches) {
        String stringCochesEntrando = "{";
        for (Coche coche : coches) {
            stringCochesEntrando += coche.getId() + ",";
        }
        labelCochesEntrando.setText(stringCochesEntrando.substring(0, stringCochesEntrando.length() - 1) + "}");
    }

    @Override
    public void cambioCochesSaliendo(List<Coche> coches) {
        String stringCochesSaliendo = "{";
        for (Coche coche : coches) {
            stringCochesSaliendo += coche.getId() + ",";
        }
        labelCochesSaliendo.setText(stringCochesSaliendo.substring(0, stringCochesSaliendo.length() - 1) + "}");
    }
}
