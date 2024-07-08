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
import java.util.ArrayList;
import java.util.List;
import codigo.codigofinal.Compra;
import codigo.codigofinal.DatabaseConection;

public class ReporteAController {
    private DatabaseConection db;

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
        List<Compra> compras = comprasRealizadasEnPeriodo(idCliente, primerafecha, segundafecha);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter);

        String reportContent = "Reporte A\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Primera Fecha: " + primerafecha + "\n" +
                "Segunda Fecha: " + segundafecha + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n" +
                "Detalle de Compras:\n";

        for (Compra compra : compras) {
            reportContent += "ID Compra: " + compra.getIdCompra() + "\n" +
                    "Fecha Compra: " + compra.getFechaCompra() + "\n" +
                    "Monto Total: $" + compra.getMontoTotal() + "\n" +
                    "Descripción: " + compra.getDescripcion() + "\n" +
                    "------\n";
        }

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteA" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(reportContent.toString());
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados");
        }
    }

    private List<Compra> comprasRealizadasEnPeriodo(String idCliente, String primerafecha, String segundafecha) {
        List<Compra> compras = new ArrayList<>();

        String query = "SELECT id_compra, fecha_compra, monto_total, descripcion FROM Compra WHERE id_cliente = ? AND fecha_compra BETWEEN ? AND ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(idCliente));
            stmt.setString(2, primerafecha); 
            stmt.setString(3, segundafecha);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCompra = rs.getInt("id_compra");
                String fechaCompra = rs.getString("fecha_compra");
                double montoTotal = rs.getDouble("monto_total");
                String descripcion = rs.getString("descripcion");

                compras.add(new Compra(idCompra, fechaCompra, montoTotal, descripcion));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compras;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
