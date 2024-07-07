module codigo.codigo {
    requires javafx.controls;
    requires javafx.fxml;


    opens codigo.codigo to javafx.fxml;
    exports codigo.codigo;
}