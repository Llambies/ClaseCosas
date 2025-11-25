module org.adrian.parkineo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.adrian.parkineo to javafx.fxml;
    exports org.adrian.parkineo;
}