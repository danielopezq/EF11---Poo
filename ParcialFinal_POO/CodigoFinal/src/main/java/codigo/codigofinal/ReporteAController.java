package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import DatabaseConnection;

public class ReporteBController {

    @FXML
    private TextField idClienteField;

    @FXML
    private TextField primerafechaField;

    @FXML
    private TextField segundafechaField;

    @FXML
    private Button generarReporteButton;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText();
            String primerafecha = primerafechaField.getText();
            String segundafecha = segundafechaField.getText();
            generarReporte(idCliente, primerafecha, segundafecha);
        });
    }

    private void generarReporte(String idCliente, String primerafecha, String segundafecha) {
        double totalGastado = totalComprasRealizadas(idCliente, primerafecha, segundafecha);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        String reportContent = "Reporte B\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Mes: " + mes + "\n" +
                "Año: " + ano + "\n" +
                "Total Gastado: $" + totalGastado + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteB" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent);
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados");
        }
    }

    private double totalComprasRealizadas(String idCliente, String primerafecha, String segundafecha) {
        double totalGastado = 0.0;

        String query = "SELECT SUM(monto_total) AS total FROM Compra WHERE id_cliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?";

        try (Connection conn = DatabaseConnection.getConnection();;
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            stmt.setInt(2, Integer.parseInt(mes));
            stmt.setInt(3, Integer.parseInt(ano));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalGastado = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalGastado;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
