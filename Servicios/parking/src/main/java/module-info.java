module com.adrian {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.adrian to javafx.fxml;
    exports com.adrian;
}
