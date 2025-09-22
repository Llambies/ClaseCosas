module com.adrian.calceventcss.calculadoraeventcss {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.calceventcss.calculadoraeventcss to javafx.fxml;
    exports com.adrian.calceventcss.calculadoraeventcss;
}