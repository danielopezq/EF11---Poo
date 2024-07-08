package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class BancoController {

    @FXML
    private StackPane rootPane;

    @FXML
    public void handleReporteA() {
        closeCurrentWindow();
    }

    @FXML
    public void handleReporteB() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ReporteB.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Generar Reporte B");
            stage.setScene(new Scene(root, 1268, 1000));
            stage.setResizable(false);
            stage.show();
            closeCurrentWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReporteC() {
        closeCurrentWindow();
    }

    @FXML
    public void handleReporteD() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ReporteD.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Generar Reporte D");
            stage.setScene(new Scene(root, 1268, 1000));
            stage.setResizable(false);
            stage.show();
            closeCurrentWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeApplication() {
        System.exit(0);
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
