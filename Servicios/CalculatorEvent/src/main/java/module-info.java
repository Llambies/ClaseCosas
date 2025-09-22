module com.adrian.calculadoraevent.calculatorevent {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.calculadoraevent.calculatorevent to javafx.fxml;
    exports com.adrian.calculadoraevent.calculatorevent;
}