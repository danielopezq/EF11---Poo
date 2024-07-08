package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

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

public class ReporteDController {

    @FXML
    private TextField facilitadorField;

    @FXML
    private Button generarReporteButton;

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> {
            String facilitador = facilitadorField.getText();
            generarReporte(facilitador);
        });
    }

    private void generarReporte(String facilitador) {
        String reporteContent = calcularClientesConFacilitador(facilitador);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteD" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reporteContent);
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados");
        }
    }

    private String calcularClientesConFacilitador(String facilitador) {
        StringBuilder reporteContent = new StringBuilder();
        String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=BCN;encrypt=true;trustServerCertificate=true;";
        String user = "root";
        String password = "password";

        String query = "SELECT c.id_cliente, COUNT(*) AS cantidad_compras, SUM(monto_total) AS total_gastado " +
                "FROM Cliente c " +
                "JOIN Compra p ON c.id_cliente = p.id_cliente " +
                "JOIN Tarjeta t ON p.id_tarjeta = t.id_tarjeta " +
                "WHERE t.facilitador = ? " +
                "GROUP BY c.id_cliente";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, facilitador);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                int cantidadCompras = rs.getInt("cantidad_compras");
                double totalGastado = rs.getDouble("total_gastado");

                reporteContent.append("ID Cliente: ").append(idCliente).append("\n")
                        .append("Cantidad de Compras: ").append(cantidadCompras).append("\n")
                        .append("Total Gastado: $").append(totalGastado).append("\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporteContent.toString();
    }

    @FXML
    public void returnToMainMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Banco Nacional de Nlogonia");
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
