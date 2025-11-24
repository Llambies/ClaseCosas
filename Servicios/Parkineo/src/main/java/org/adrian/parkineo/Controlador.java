package org.adrian.parkineo;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controlador {

    @FXML
    private TextField plazasField;

    @FXML
    private TextField cochesField;

    @FXML
    private TextField entradasField;

    @FXML
    private TextField salidasField;

    @FXML
    private TextField tiempoField;

    @FXML
    private TextField plazasElectricasField;

    @FXML
    private TextField plazasDiscapacitadosField;

    @FXML
    private Button iniciarButton;

    @FXML
    private Button listaEsperaButton;

    @FXML
    private TextArea estadisticasArea;

    private Parking parking;
    private AnimationTimer actualizador;

    @FXML
    protected void iniciarSimulacion() {
        try {
            int plazas = Integer.parseInt(plazasField.getText());
            int numCoches = Integer.parseInt(cochesField.getText());
            int entradas = Integer.parseInt(entradasField.getText());
            int salidas = Integer.parseInt(salidasField.getText());
            int tiempo = Integer.parseInt(tiempoField.getText());
            int plazasElectricas = Integer.parseInt(plazasElectricasField.getText());
            int plazasDiscapacitados = Integer.parseInt(plazasDiscapacitadosField.getText());

            // Validaciones
            if (plazas <= 0 || numCoches <= 0 || entradas <= 0 || salidas <= 0 || tiempo <= 0) {
                mostrarError("Todos los valores deben ser mayores a 0");
                return;
            }

            if (plazasElectricas < 0 || plazasDiscapacitados < 0) {
                mostrarError("Las plazas especiales no pueden ser negativas");
                return;
            }

            if (plazasElectricas + plazasDiscapacitados > plazas) {
                mostrarError("Las plazas especiales no pueden exceder el total de plazas");
                return;
            }

            // Crear simulador
            parking = new Parking(plazas, entradas, salidas, plazasElectricas, plazasDiscapacitados);
            parking.iniciarSimulacion(numCoches, tiempo);

            // Deshabilitar entrada de datos
            plazasField.setDisable(true);
            cochesField.setDisable(true);
            entradasField.setDisable(true);
            salidasField.setDisable(true);
            tiempoField.setDisable(true);
            plazasElectricasField.setDisable(true);
            plazasDiscapacitadosField.setDisable(true);
            iniciarButton.setDisable(true);

            // Iniciar actualizador de estadísticas
            iniciarActualizador();

        } catch (NumberFormatException e) {
            mostrarError("Por favor ingresa números válidos");
        }
    }

    private void iniciarActualizador() {
        actualizador = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (parking != null) {
                    actualizarEstadisticas();

                    if (!parking.isSimulacionActiva()) {
                        this.stop();
                        finalizarSimulacion();
                    }
                }
            }
        };
        actualizador.start();
    }

    private void actualizarEstadisticas() {
        if (parking == null)
            return;

        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS DEL ESTACIONAMIENTO ===\n\n");
        sb.append(String.format("Aforo máximo: %d plazas\n", parking.plazasNormales));
        sb.append(String.format("  - Plazas normales: %d\n",
                parking.plazasNormales - parking.plazasElectricas - parking.plazasDiscapacitados));
        sb.append(String.format("  - Plazas para eléctricos: %d\n", parking.plazasElectricas));
        sb.append(String.format("  - Plazas para discapacitados: %d\n", parking.plazasDiscapacitados));
        sb.append(String.format("Entradas activas: %d\n", parking.numEntradas));
        sb.append(String.format("Salidas activas: %d\n\n", parking.numSalidas));

        sb.append("--- ESTADO ACTUAL ---\n");
        sb.append(String.format("Coches estacionados: %d/%d\n",
                parking.cochesEnEstacionamiento, parking.plazasNormales));
        sb.append(String.format("  - En plazas normales: %d\n",
                parking.cochesEnEstacionamiento - parking.cochesEnEstacionamientoElectricos
                        - parking.cochesEnEstacionamientoDiscapacitados));
        sb.append(String.format("  - En plazas eléctricos: %d/%d\n",
                parking.cochesEnEstacionamientoElectricos, parking.plazasElectricas));
        sb.append(String.format("  - En plazas discapacitados: %d/%d\n",
                parking.cochesEnEstacionamientoDiscapacitados, parking.plazasDiscapacitados));
        sb.append(String.format("En cola de espera: %d\n", parking.cochesEnEspera));
        sb.append(String.format("Total de accesos: %d\n", parking.totalAccesos));
        sb.append(String.format("Recaudación total: €%.2f\n\n", parking.recaudacionTotal));

        sb.append("--- OCUPACIÓN ---\n");
        double porcentajeOcupacion = (parking.cochesEnEstacionamiento * 100.0) / parking.plazasNormales;
        sb.append(String.format("Ocupación: %.1f%%\n", porcentajeOcupacion));
        sb.append(crearBarraOcupacion(porcentajeOcupacion));

        estadisticasArea.setText(sb.toString());
    }

    private String crearBarraOcupacion(double porcentaje) {
        int bloques = (int) (porcentaje / 5);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            barra.append(i < bloques ? "█" : "░");
        }
        barra.append("] ");
        barra.append(String.format("%.1f%%\n", porcentaje));
        return barra.toString();
    }

    private void finalizarSimulacion() {
        if (actualizador != null) {
            actualizador.stop();
        }

        // Habilitar botón de lista de espera
        listaEsperaButton.setDisable(false);

        // Mostrar resumen final
        StringBuilder sb = new StringBuilder();
        sb.append(estadisticasArea.getText());
        sb.append("\n=== SIMULACIÓN FINALIZADA ===\n");
        sb.append("Haz clic en 'Ver Lista de Espera' para ver el historial completo.");
        estadisticasArea.setText(sb.toString());
    }

    @FXML
    protected void abrirListaEspera() {
        if (parking == null) {
            mostrarError("No hay datos de simulación disponibles");
            return;
        }

        // Crear nueva ventana
        Stage stage = new Stage();
        stage.setTitle("Lista de Espera - Historial de Coches");
        stage.setWidth(600);
        stage.setHeight(500);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(10);

        Label titulo = new Label("Historial Completo de Coches");
        titulo.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        TextArea historialArea = new TextArea();
        historialArea.setWrapText(true);
        historialArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("HISTORIAL COMPLETO DE ACCESOS\n");
        sb.append("=".repeat(60)).append("\n\n");

        historialArea.setText(sb.toString());

        vbox.getChildren().addAll(titulo, historialArea);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
