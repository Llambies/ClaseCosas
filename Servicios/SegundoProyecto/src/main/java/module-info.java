module com.adrian.primerproyecto.segundoproyecto {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.primerproyecto.segundoproyecto to javafx.fxml;
    exports com.adrian.primerproyecto.segundoproyecto;
}