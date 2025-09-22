module com.adrian.converter.currencyconverter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.converter.currencyconverter to javafx.fxml;
    exports com.adrian.converter.currencyconverter;
}