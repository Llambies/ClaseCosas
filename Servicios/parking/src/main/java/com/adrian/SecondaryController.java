package com.adrian;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class SecondaryController implements Initializable {

    @FXML
    ListView<String> listaEventos;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.err.println("Holi");
        listaEventos.getItems().setAll(App.parking.eventos);
    }
}