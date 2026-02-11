package com.llambies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;
import java.util.Map;

public class ClienteController {

    private final ParkinAPI api = new ParkinAPI("http://localhost:3000");
    private String estacionamientoId;
    private Timeline timer;

    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private Label lblDisponibilidad;
    @FXML
    private Button btnAparcar;
    @FXML
    private VBox panelDisponible;
    @FXML
    private VBox panelAparcado;
    @FXML
    private Label lblPlazaAsignada;
    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblPrecio;
    @FXML
    private Label lblEstado;
    @FXML
    private Button btnVolverInicio;

    @FXML
    private void initialize() {
        comboTipo.getItems().addAll("normal", "electrico", "discapacitado");
        comboTipo.getSelectionModel().selectFirst();
    }

    @FXML
    private void comprobarDisponibilidad() {
        String tipo = comboTipo.getValue();
        if (tipo == null || tipo.isBlank()) {
            lblDisponibilidad.setText("Selecciona un tipo de plaza.");
            return;
        }
        lblEstado.setText("");
        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() throws Exception {
                return api.plazasDisponibles(tipo);
            }
        };
        task.setOnSucceeded(e -> {
            int count = task.getValue();
            if (count > 0) {
                lblDisponibilidad.setText("Hay " + count + " plaza(s) disponible(s) para " + tipo + ".");
                btnAparcar.setVisible(true);
                btnAparcar.setManaged(true);
            } else {
                lblDisponibilidad.setText("No hay plazas disponibles para " + tipo + ".");
                btnAparcar.setVisible(false);
                btnAparcar.setManaged(false);
            }
        });
        task.setOnFailed(e -> {
            lblDisponibilidad.setText("Error: " + task.getException().getMessage());
            btnAparcar.setVisible(false);
            btnAparcar.setManaged(false);
        });
        new Thread(task).start();
    }

    @FXML
    private void aparcar() {
        String tipo = comboTipo.getValue();
        if (tipo == null || tipo.isBlank()) return;
        lblEstado.setText("");
        Task<Map<String, Object>> task = new Task<>() {
            @Override
            protected Map<String, Object> call() throws Exception {
                return api.iniciarEstacionamiento(tipo);
            }
        };
        task.setOnSucceeded(e -> {
            Map<String, Object> res = task.getValue();
            Object idObj = res.get("id");
            estacionamientoId = idObj != null ? idObj.toString() : null;
            if (estacionamientoId != null) {
                int numero = res.get("numero") != null ? ((Number) res.get("numero")).intValue() : 0;
                int planta = res.get("planta") != null ? ((Number) res.get("planta")).intValue() : 0;
                lblPlazaAsignada.setText("Plaza asignada: " + numero + " (Planta " + planta + ")");
                panelDisponible.setVisible(false);
                panelDisponible.setManaged(false);
                panelAparcado.setVisible(true);
                panelAparcado.setManaged(true);
                btnVolverInicio.setVisible(false);
                btnVolverInicio.setManaged(false);
                iniciarContador();
            }
        });
        task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    private void iniciarContador() {
        if (timer != null) timer.stop();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> actualizarContador()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void actualizarContador() {
        if (estacionamientoId == null) return;
        Task<Map<String, Object>> task = new Task<>() {
            @Override
            protected Map<String, Object> call() throws Exception {
                return api.obtenerEstacionamientoActivo(estacionamientoId);
            }
        };
        task.setOnSucceeded(e -> {
            Map<String, Object> res = task.getValue();
            int minutos = res.get("minutos") != null ? ((Number) res.get("minutos")).intValue() : 0;
            double total = res.get("total") != null ? ((Number) res.get("total")).doubleValue() : 0;
            lblTiempo.setText(minutos + " min");
            lblPrecio.setText(String.format("%.2f €", total));
        });
        task.setOnFailed(e -> {
            if (timer != null) timer.stop();
        });
        new Thread(task).start();
    }

    @FXML
    private void abandonarParking() {
        if (estacionamientoId == null) return;
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        lblEstado.setText("");
        Task<Map<String, Object>> task = new Task<>() {
            @Override
            protected Map<String, Object> call() throws Exception {
                return api.finalizarEstacionamiento(estacionamientoId);
            }
        };
        task.setOnSucceeded(e -> {
            Map<String, Object> res = task.getValue();
            double total = res.get("total") != null ? ((Number) res.get("total")).doubleValue() : 0;
            lblEstado.setText("Estacionamiento finalizado. Total a pagar: " + String.format("%.2f", total) + " €");
            estacionamientoId = null;
            panelAparcado.setVisible(false);
            panelAparcado.setManaged(false);
            panelDisponible.setVisible(true);
            panelDisponible.setManaged(true);
            lblDisponibilidad.setText("");
            btnAparcar.setVisible(false);
            btnAparcar.setManaged(false);
            btnVolverInicio.setVisible(true);
            btnVolverInicio.setManaged(true);
        });
        task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
        new Thread(task).start();
    }

    @FXML
    private void volverInicio() {
        if (estacionamientoId != null) return;
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        panelDisponible.setVisible(true);
        panelDisponible.setManaged(true);
        panelAparcado.setVisible(false);
        panelAparcado.setManaged(false);
        btnVolverInicio.setVisible(false);
        btnVolverInicio.setManaged(false);
        lblDisponibilidad.setText("");
        lblEstado.setText("");
        comboTipo.getSelectionModel().selectFirst();
    }

    @FXML
    private void abrirModalAdmin() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Acceso administrador");
        dialog.setHeaderText("Introduce la contraseña de administrador");
        dialog.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setPrefWidth(220);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("PIN Admin:"), 0, 0);
        grid.add(passwordField, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.setOnShown(e -> passwordField.requestFocus());

        dialog.setResultConverter(btn -> {
            if (btn == javafx.scene.control.ButtonType.OK) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(pin -> {
            if (pin == null || pin.isBlank()) {
                lblEstado.setText("Introduce el PIN de administrador.");
                return;
            }
            lblEstado.setText("Verificando...");
            Task<Boolean> task = new Task<>() {
                @Override
                protected Boolean call() throws Exception {
                    return api.verificarPinAdmin(pin);
                }
            };
            task.setOnSucceeded(e -> {
                if (Boolean.TRUE.equals(task.getValue())) {
                    try {
                        App.setRoot("primary");
                    } catch (IOException ex) {
                        lblEstado.setText("Error: " + ex.getMessage());
                    }
                } else {
                    lblEstado.setText("PIN incorrecto.");
                }
            });
            task.setOnFailed(e -> lblEstado.setText("Error: " + task.getException().getMessage()));
            new Thread(task).start();
        });
    }
}
