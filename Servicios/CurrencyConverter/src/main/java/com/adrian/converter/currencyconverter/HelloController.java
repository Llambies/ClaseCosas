package com.adrian.converter.currencyconverter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    private Text resultado;
    @FXML
    private TextField cantidadAConvertir;
    @FXML
    private ToggleGroup conversiones;

    @FXML
    private void onTextChanged(){
        RadioMenuItem selectedItem = (RadioMenuItem) conversiones.getSelectedToggle();
        String tipoConversion = selectedItem.getText();
        String monedaOrigen = tipoConversion.substring(0,3);
        String monedaDestino = tipoConversion.substring(7,10);
        double cantidad = 0;
        try {
            cantidad = Double.parseDouble(cantidadAConvertir.getText());
        } catch (NumberFormatException e) {
            resultado.setText("Ingrese un valor valido");
            return;
        }

        resultado.setText(String.format("%.3f %s = %.3f %s", cantidad, monedaOrigen, cantidad/Ratio(monedaOrigen)*Ratio(monedaDestino), monedaDestino));
    }

    private float Ratio(String moneda){
        if(moneda.equals("USD")){
            return 1.07f;
        } else if (moneda.equals("GBP")) {
            return 0.87f;
        }else {
            return 1f;
        }
    }

}
