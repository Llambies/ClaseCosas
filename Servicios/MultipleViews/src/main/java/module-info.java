module com.adrian.multivista.multipleviews {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.multivista.multipleviews to javafx.fxml;
    exports com.adrian.multivista.multipleviews;
}