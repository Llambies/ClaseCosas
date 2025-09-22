module com.adrian.notepadfx.notepadjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.adrian.notepadfx.notepadjavafx to javafx.fxml;
    exports com.adrian.notepadfx.notepadjavafx;
}