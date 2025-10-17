module com.adrian.procesos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.procesos to javafx.fxml;
    exports com.adrian.procesos;
}