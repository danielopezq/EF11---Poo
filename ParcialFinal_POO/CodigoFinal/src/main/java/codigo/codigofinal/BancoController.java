package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BancoController {

    //Las otras opciones se agregan ya cuando mis compañeros hagan sus respectivos Fxml, pero todo sigue la misma logica
    @FXML
    public void handleReporteA() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ReporteA.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Generar Reporte A");
            stage.setScene(new Scene(root, 1268, 1000));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleReporteC() {

    }

    @FXML
    public void handleReporteD() {

    }


}
