module com.llambies {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.llambies to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.llambies;
}
