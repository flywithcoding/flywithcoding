module com.example.myapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires javafx.swing;

    opens com.example.myapplication to javafx.fxml;
    exports com.example.myapplication;
}