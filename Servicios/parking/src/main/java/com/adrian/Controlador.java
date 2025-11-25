package com.adrian;

import java.io.IOException;

import com.adrian.model.Parking;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class Controlador {

    @FXML
    TextField fieldNombre;
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

        Parking p = new Parking(nombre, plazas, plazasElectricas, plazasEspeciales, precio, precioElectrico,
                precioEspecial, accesos);
        App.guardarConfiguracion(p);
        aviso(AlertType.INFORMATION, "Configurado correctamente",
                "El parking ha sido configurado y guardado correctamente", "Guardado", Color.color(0.1, 0.8, 0.1));

    }

    @FXML
    private void switchToSecondary() throws IOException {
        // App.abrirVentana("secondary");

        try {
            App.iniciarSimulacion();
        } catch (Exception e) {
            aviso(AlertType.ERROR, "Error al iniciar simulacion", e.getMessage(), "Error", Color.color(0.8, 0.1, 0.1));
        }
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
}
