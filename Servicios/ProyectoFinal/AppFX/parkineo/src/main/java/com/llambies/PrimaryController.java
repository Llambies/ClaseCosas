package com.llambies;

import javafx.concurrent.Task;
import java.io.IOException;
import javafx.fxml.FXML;
import java.util.List;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private final ParkinAPI api = new ParkinAPI("http://localhost:3000");

    @FXML
    private void cargarPlazas() {
        Task<List<Plaza>> task = new Task<>() {
            @Override
            protected List<Plaza> call() throws Exception {
                return api.listarPlazas();
            }
        };
        task.setOnSucceeded(e -> {
            List<Plaza> lista = task.getValue();
            System.out.println(lista);
        });
        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            System.out.println(ex.getMessage());
        });
        new Thread(task).start();
    }
}
