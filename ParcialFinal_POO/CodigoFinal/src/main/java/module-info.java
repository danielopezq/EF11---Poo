module codigo.codigofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;


    opens codigo.codigofinal to javafx.fxml;
    exports codigo.codigofinal;
}