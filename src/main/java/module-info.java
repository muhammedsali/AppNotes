module com.example.appnotes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.logging;

    opens com.example.appnotes to javafx.fxml;
    exports com.example.appnotes;
}
