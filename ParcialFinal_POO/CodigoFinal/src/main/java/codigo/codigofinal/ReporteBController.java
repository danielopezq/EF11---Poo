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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteBController {

    @FXML
    private TextField idClienteField;

    @FXML
    private TextField mesField;

    @FXML
    private TextField anoField;

    @FXML
    private Button generarReporteButton;

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText();
            String mes = mesField.getText();
            String ano = anoField.getText();
            generarReporte(idCliente, mes, ano);
        });
    }

    private void generarReporte(String idCliente, String mes, String ano) {
        double totalGastado = calcularTotalGastado(idCliente, mes, ano);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        String reportContent = "Reporte B\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Mes: " + mes + "\n" +
                "Año: " + ano + "\n" +
                "Total Gastado: $" + totalGastado + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String fileName = desktopPath + "/ReporteB" + timestamp + ".txt";

        File desktopDir = new File(desktopPath);
        if (!desktopDir.exists()) {
            desktopDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent);
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados: " + e.getMessage());
        }
    }

    private double calcularTotalGastado(String idCliente, String mes, String ano) {
        double totalGastado = 0.0;
        String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=BCN;encrypt=true;trustServerCertificate=true;";
        String user = "root";
        String password = "password";

        String query = "SELECT SUM(monto_total) AS total FROM Compra WHERE id_cliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            try (Connection conn = DriverManager.getConnection(url, user, password);
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
        } catch (ClassNotFoundException e) {
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
}
