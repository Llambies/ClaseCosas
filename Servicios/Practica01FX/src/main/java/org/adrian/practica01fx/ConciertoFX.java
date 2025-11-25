package org.adrian.practica01fx;

import javafx.fxml.FXML;
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
    private TextField txtTaquillas;
    @FXML
    private TextArea txtResumen;

    private Concierto concierto;
    private int fansTerminados;
    private int entradasVendidas;
    private StringBuilder resumenSimulacion;

    public void guardarConcierto() {
        try {
            this.concierto = new Concierto(txtNombre.getText(), Integer.parseInt(txtAforo.getText()),
                    Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtFans.getText()), Integer.parseInt(txtTaquillas.getText()));
            txtResumen.setText(concierto.toString());
        } catch (Exception e) {
            txtResumen.setText("Error: " + e.getMessage());
        }
    }

    public void iniciarSimulacion() {

        if (concierto == null) {
            txtResumen.setText("Error: No se ha guardado el concierto");
            return;
        }

        try {
            this.fansTerminados = 0;
            this.entradasVendidas = 0;
            this.resumenSimulacion = new StringBuilder();
            resumenSimulacion.append("Iniciando simulacion...\n");
            resumenSimulacion.append("Fans: ").append(concierto.getFans()).append("\n");
            resumenSimulacion.append("Entradas disponibles: ").append(concierto.getAforo()).append("\n");
            resumenSimulacion.append("Precio: ").append(concierto.getPrecio()).append("\n\n");
            txtResumen.setText(resumenSimulacion.toString());
            
            for (int i = 0; i < concierto.getFans(); i++) {
                Thread thread = new Thread(new FanFX(concierto, this, i));
                thread.start();
            }
        } catch (Exception e) {
            txtResumen.setText("Error: " + e.getMessage());
        }
    }

    public synchronized void fanTerminado(int idFan, boolean compraExitosa) {
        fansTerminados++;
        if (compraExitosa) {
            entradasVendidas++;
            resumenSimulacion.append("Fan ").append(idFan).append(" compró entrada exitosamente\n");
        } else {
            resumenSimulacion.append("Fan ").append(idFan).append(" NO pudo comprar entrada\n");
        }
        
        if (fansTerminados == concierto.getFans()) {
            resumenSimulacion.append("\n--- SIMULACION TERMINADA ---\n");
            resumenSimulacion.append("Entradas vendidas: ").append(entradasVendidas).append("/").append(concierto.getAforo()).append("\n");
            resumenSimulacion.append("Ingresos totales: ").append(entradasVendidas * concierto.getPrecio());
        }
        txtResumen.setText(resumenSimulacion.toString());
    }
}
