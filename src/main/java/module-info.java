module com.example.chequers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;


    opens com.example.chequers to javafx.fxml;
    exports com.example.chequers;
}