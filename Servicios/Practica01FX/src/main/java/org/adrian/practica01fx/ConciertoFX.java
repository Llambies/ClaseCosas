package org.adrian.practica01fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConciertoFX {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtAforo;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtFans;
    @FXML
    private TextArea txtResumen;

    public void guardarConcierto() {
        try {
            Concierto concierto = new Concierto(txtNombre.getText(), Integer.parseInt(txtAforo.getText()), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtFans.getText()));
            txtResumen.setText(concierto.toString());
        } catch (Exception e) {
            txtResumen.setText("Error: " + e.getMessage());
        }
    }
}
