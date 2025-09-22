package com.adrian.multivista.multipleviews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    void cambiarVista2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("vista2.fxml"));
        Scene escenaVista1 = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(escenaVista1);
        stage.show();
    }

    @FXML
    void cambiarVista3(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("vista3.fxml"));
        Scene escenaVista1 = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(escenaVista1);
        stage.show();
    }

    @FXML
    void volverAHello(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene escenaVista1 = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(escenaVista1);
        stage.show();
    }

}
