module com.adrian.calculadora.calculadora {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.calculadora.calculadora to javafx.fxml;
    exports com.adrian.calculadora.calculadora;
}