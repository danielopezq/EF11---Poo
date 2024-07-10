package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuMainController {

    @FXML
    private StackPane rootPane; // 00148023 Panel raíz de la interfaz

    @FXML
    private void handleCompraOnline() {
        // 00148023 Cargar la escena de compra en línea
        loadScene("CompraOnline.fxml", "Compra en Línea");
    }

    @FXML
    private void handleReportes() {
        // 00148023 Cargar la escena de reportes
        loadScene("MenuReportes.fxml", "Reportes");
    }

    @FXML
    private void handleSalir() {
        // 00148023 Cerrar la aplicación
        System.exit(0);
    }

    private void loadScene(String fxmlFile, String title) {
        try {
            // 00148023 Cargar el archivo FXML especificado
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            // 00148023 Crear y mostrar una nueva escena con el título especificado
            Stage stage = new Stage();
            stage.setTitle(title); // 00148023 Establecer el título de la ventana
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establecer la escena principal
            stage.setResizable(false); // 00148023 Hacer la ventana no redimensionable
            stage.show(); // 00148023 Mostrar la ventana
            // 00148023 Cerrar la ventana actual
            closeCurrentWindow();
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción de IO
        }
    }

    private void closeCurrentWindow() {
        // 00148023 Obtener la ventana actual y cerrarla
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close(); // 00148023 Cerrar la ventana
    }
}
