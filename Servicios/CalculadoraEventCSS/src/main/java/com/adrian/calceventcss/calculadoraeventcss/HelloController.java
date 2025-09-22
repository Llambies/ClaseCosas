package com.adrian.calceventcss.calculadoraeventcss;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField firstNumberField;
    @FXML
    private TextField secondNumberField;
    @FXML
    private ComboBox<String> operationCombo;
    @FXML
    private TextField resultField;

    @FXML
    private void onGo() {
        String aText = firstNumberField.getText();
        String bText = secondNumberField.getText();
        String op = operationCombo.getValue();

        if (op == null || op.isBlank()) {
            resultField.setText("Selecciona operación");
            return;
        }

        double a, b;
        try {
            a = Double.parseDouble(aText);
            b = Double.parseDouble(bText);
        } catch (NumberFormatException e) {
            resultField.setText("Números inválidos");
            return;
        }

        double res;
        try {
            if (op.startsWith("Suma")) {
                res = a + b;
            } else if (op.startsWith("Resta")) {
                res = a - b;
            } else if (op.startsWith("Multiplicación")) {
                res = a * b;
            } else if (op.startsWith("División")) {
                if (b == 0.0) {
                    resultField.setText("División por 0");
                    return;
                }
                res = a / b;
            } else {
                resultField.setText("Operación desconocida");
                return;
            }
        } catch (Exception ex) {
            resultField.setText("Error");
            return;
        }

        resultField.setText(Double.toString(res));
    }
}
