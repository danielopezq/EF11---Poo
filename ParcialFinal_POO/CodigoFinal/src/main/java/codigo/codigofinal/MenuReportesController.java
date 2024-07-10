package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuReportesController {

    @FXML
    private StackPane rootPane; // 00148023 Panel raíz de la interfaz

    @FXML
    public void handleReporteA() {
        // 00148023 Cargar la escena del reporte A
        loadScene("ReporteA.fxml", "Generar Reporte A");
    }

    @FXML
    public void handleReporteB() {
        // 00148023 Cargar la escena del reporte B
        loadScene("ReporteB.fxml", "Generar Reporte B");
    }

    @FXML
    public void handleReporteC() {
        // 00148023 Cargar la escena del reporte C
        loadScene("ReporteC.fxml", "Generar Reporte C");
    }

    @FXML
    public void handleReporteD() {
        // 00148023 Cargar la escena del reporte D
        loadScene("ReporteD.fxml", "Generar Reporte D");
    }

    @FXML
    public void returnToMainMenu() {
        try {
            // 00148023 Cargar el archivo FXML del menú principal
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            // 00148023 Crear y mostrar una nueva escena del menú principal
            Stage stage = new Stage();
            stage.setTitle("Banco Nacional de Nlogonia"); // 00148023 Establecer el título de la ventana
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establecer la escena principal
            stage.setResizable(false); // 00148023 Hacer la ventana no redimensionable
            stage.show(); // 00148023 Mostrar la ventana principal
            // 00148023 Cerrar la ventana actual
            closeCurrentWindow();
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción de IO
        }
    }

    private void loadScene(String fxml, String title) {
        try {
            // 00148023 Cargar el archivo FXML especificado
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
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
