package com.llambies;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class LoginController {

    private final ParkinAPI api = new ParkinAPI("http://localhost:3000");

    @FXML
    private PasswordField txtPin;
    @FXML
    private Label lblEstado;

    @FXML
    private void onAdmin() {
        String pin = txtPin.getText();
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
    }

    @FXML
    private void onCliente() {
        try {
            App.setRoot("cliente");
        } catch (IOException ex) {
            lblEstado.setText("Error: " + ex.getMessage());
        }
    }
}
