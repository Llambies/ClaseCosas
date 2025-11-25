package com.adrian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.adrian.model.Coche;
import com.adrian.model.Parking;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Parking parking;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static void abrirVentana(String fxml) throws IOException {
        Scene escena = new Scene(loadFXML(fxml));
        Stage escenario = new Stage();

        escenario.setTitle("Nombre");
        escenario.setScene(escena);
        escenario.show();

    }

    static void guardarConfiguracion(Parking p) {
        parking = p;
    }

    static void iniciarSimulacion() throws Exception {
        if (parking == null) {
            throw new Exception("Parking no configurado");
        }

        for (int i = 0; i < 1000; i++) {
            Coche c = new Coche(parking, (Math.random() < 0.1), (Math.random() < 0.1));
            Thread hilo = new Thread(c);
            hilo.start();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}