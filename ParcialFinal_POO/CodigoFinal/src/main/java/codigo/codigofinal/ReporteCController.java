package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

public class ReporteCController {

    @FXML
    private TextField idClienteField;

    @FXML
    private Button generarReporteButton;

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText();
            generarReporte(idCliente);
        });
    }

    private void generarReporte(String idCliente) {
        String nombreCliente = "";
        StringBuilder tarjetasCredito = new StringBuilder();
        StringBuilder tarjetasDebito = new StringBuilder();

        String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=BCNN;encrypt=true;trustServerCertificate=true;";
        String user = "ParcialFinal";
        String password = "parcialfinalpoo";

        String query = "DECLARE @ClienteID INT = ?; " +
                "SELECT " +
                "    c.nombre_completo AS NombreCliente, " +
                "    CASE " +
                "        WHEN t.tipo_tarjeta = 'Credito' THEN 'Tarjetas de crédito' " +
                "        WHEN t.tipo_tarjeta = 'Debito' THEN 'Tarjetas de débito' " +
                "    END AS TipoTarjeta, " +
                "    'XXXX XXXX XXXX ' + RIGHT(t.numero_tarjeta, 4) AS NumeroTarjeta " +
                "FROM " +
                "    Tarjeta t " +
                "JOIN " +
                "    Cliente c ON t.id_cliente = c.id_cliente " +
                "WHERE " +
                "    t.id_cliente = @ClienteID " +
                "ORDER BY " +
                "    t.tipo_tarjeta, t.numero_tarjeta;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(idCliente));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                nombreCliente = rs.getString("NombreCliente");
                String tipoTarjeta = rs.getString("TipoTarjeta");
                String numeroTarjeta = rs.getString("NumeroTarjeta");

                if (tipoTarjeta.equals("Tarjetas de crédito")) {
                    tarjetasCredito.append(numeroTarjeta).append("\n");
                } else if (tipoTarjeta.equals("Tarjetas de débito")) {
                    tarjetasDebito.append(numeroTarjeta).append("\n");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        String reportContent = "Reporte C:\n" +
                "Tarjetas correspondientes al cliente " + nombreCliente + ":\n" +
                "Tarjetas de crédito:\n" +
                (tarjetasCredito.length() > 0 ? tarjetasCredito.toString() : "N/A\n") +
                "Tarjetas de débito:\n" +
                (tarjetasDebito.length() > 0 ? tarjetasDebito.toString() : "N/A\n") +
                "\nGenerado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteC_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent);
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void returnToMainMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
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
}
