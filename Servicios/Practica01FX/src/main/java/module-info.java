module org.adrian.practica01fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.adrian.practica01fx to javafx.fxml;
    exports org.adrian.practica01fx;
}