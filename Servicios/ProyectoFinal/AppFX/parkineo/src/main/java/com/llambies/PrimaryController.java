package com.llambies;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PrimaryController {

    private final ParkinAPI api = new ParkinAPI("http://localhost:3000");
    private Plaza selectedPlaza;

    @FXML
    private TableView<Plaza> tablaPlazas;
    @FXML
    private TableColumn<Plaza, Number> colNumero;
    @FXML
    private TableColumn<Plaza, Number> colPlanta;
    @FXML
    private TableColumn<Plaza, String> colTipo;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtPlanta;
    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private Label lblEstado;

    @FXML
    private void initialize() {
        comboTipo.getItems().addAll("normal", "electrico", "discapacitado");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colPlanta.setCellValueFactory(new PropertyValueFactory<>("planta"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        refrescarPlazas();
    }

    @FXML
    private void onSeleccionarPlaza() {
        selectedPlaza = tablaPlazas.getSelectionModel().getSelectedItem();
        if (selectedPlaza != null) {
            txtNumero.setText(String.valueOf(selectedPlaza.getNumero()));
            txtPlanta.setText(String.valueOf(selectedPlaza.getPlanta()));
            comboTipo.setValue(selectedPlaza.getTipo());
        }
    }

    @FXML
    private void refrescarPlazas() {
        Task<List<Plaza>> task = new Task<>() {
            @Override
            protected List<Plaza> call() throws Exception {
                return api.listarPlazas();
            }
        };
        task.setOnSucceeded(e -> {
            tablaPlazas.getItems().setAll(task.getValue());
            lblEstado.setText("Listadas " + task.getValue().size() + " plazas.");
        });
        task.setOnFailed(e -> {
            lblEstado.setText("Error: " + task.getException().getMessage());
        });
        new Thread(task).start();
    }

    @FXML
    private void insertarPlaza() {
        Plaza p = leerPlazaDelFormulario();
        if (p == null) return;

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                api.insertarPlaza(p);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            lblEstado.setText("Plaza insertada correctamente.");
            limpiarFormulario();
            refrescarPlazas();
        });
        task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    @FXML
    private void actualizarPlaza() {
        if (selectedPlaza == null || selectedPlaza.getId() == null) {
            lblEstado.setText("Selecciona una plaza de la tabla para actualizar.");
            return;
        }
        Plaza p = leerPlazaDelFormulario();
        if (p == null) return;
        p.setId(selectedPlaza.getId());

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                api.actualizarPlaza(p);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            lblEstado.setText("Plaza actualizada correctamente.");
            limpiarFormulario();
            selectedPlaza = null;
            refrescarPlazas();
        });
        task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    @FXML
    private void borrarPlaza() {
        Plaza p = tablaPlazas.getSelectionModel().getSelectedItem();
        if (p == null || p.getId() == null) {
            lblEstado.setText("Selecciona una plaza de la tabla para borrar.");
            return;
        }

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                api.borrarPlaza(p.getId());
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            lblEstado.setText("Plaza borrada correctamente.");
            limpiarFormulario();
            selectedPlaza = null;
            refrescarPlazas();
        });
        task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    private Plaza leerPlazaDelFormulario() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            int planta = Integer.parseInt(txtPlanta.getText().trim());
            String tipo = comboTipo.getValue() != null ? comboTipo.getValue().trim() : "";
            if (tipo.isEmpty()) {
                lblEstado.setText("Selecciona un tipo de plaza.");
                return null;
            }
            return new Plaza(numero, planta, tipo);
        } catch (NumberFormatException ex) {
            lblEstado.setText("Número y Planta deben ser enteros válidos.");
            return null;
        }
    }

    private void limpiarFormulario() {
        txtNumero.clear();
        txtPlanta.clear();
        comboTipo.setValue(null);
    }

    @FXML
    private void cerrarSesion() throws IOException {
        App.setRoot("cliente");
    }
}
