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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteBController {
    // 00148023 Conexión a la base de datos
    private DatabaseConnection db;

    @FXML
    private TextField idClienteField; // 00148023 Campo de texto para el ID del cliente
    @FXML
    private TextField mesField; // 00148023 Campo de texto para el mes del reporte
    @FXML
    private TextField anoField; // 00148023 Campo de texto para el año del reporte
    @FXML
    private Button generarReporteButton; // 00148023 Botón para generar el reporte
    @FXML
    private StackPane rootPane; // 00148023 Panel raíz de la interfaz

    @FXML
    public void initialize() {
        // 00148023 Configurar el evento de clic del botón de generar reporte
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText(); // 00148023 Obtener valor del campo ID cliente
            String mes = mesField.getText(); // 00148023 Obtener valor del campo mes
            String ano = anoField.getText(); // 00148023 Obtener valor del campo año
            // 00148023 Llamar al método generarReporte con los datos obtenidos
            generarReporte(idCliente, mes, ano);
        });
    }

    private void generarReporte(String idCliente, String mes, String ano) {
        // 00148023 Calcular el total gastado por el cliente en el mes y año especificados
        double totalGastado = calcularTotalGastado(idCliente, mes, ano);

        // 00148023 Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // 00148023 Formatear la fecha actual para el nombre del archivo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter); // 00148023 Convertir la fecha a string formateado

        // 00148023 Contenido del reporte
        String reportContent = "Reporte B\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Mes: " + mes + "\n" +
                "Año: " + ano + "\n" +
                "Total Gastado: $" + totalGastado + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        // 00148023 Ruta del escritorio del usuario
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        // 00148023 Nombre del archivo de reporte
        String fileName = desktopPath + "/ReporteB_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // 00148023 Escribir el contenido del reporte en el archivo
            writer.write(reportContent);
            // 00148023 Mostrar alerta de éxito
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción de IO
            // 00148023 Mostrar alerta de error
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados: " + e.getMessage());
        }
    }

    private double calcularTotalGastado(String idCliente, String mes, String ano) {
        double totalGastado = 0.0; // 00148023 Inicializar variable para el total gastado

        // 00148023 Consulta SQL para sumar el total gastado por el cliente en el mes y año especificados
        String query = "SELECT SUM(monto_total) AS total FROM Compra WHERE id_cliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL

            stmt.setInt(1, Integer.parseInt(idCliente)); // 00148023 Establecer ID del cliente en la consulta
            stmt.setInt(2, Integer.parseInt(mes)); // 00148023 Establecer mes en la consulta
            stmt.setInt(3, Integer.parseInt(ano)); // 00148023 Establecer año en la consulta

            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecutar la consulta y obtener el resultado

            if (rs.next()) {
                // 00148023 Obtener el total gastado del resultado de la consulta
                totalGastado = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
        }

        return totalGastado; // 00148023 Retornar el total gastado
    }

    private void showAlert(String title, String content) {
        // 00148023 Crear y mostrar una alerta de información
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // 00148023 Establecer el título de la alerta
        alert.setHeaderText(null); // 00148023 Establecer el encabezado de la alerta
        alert.setContentText(content); // 00148023 Establecer el contenido de la alerta
        alert.showAndWait(); // 00148023 Mostrar la alerta y esperar a que el usuario la cierre
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

    @FXML
    public void closeApplication() {
        // 00148023 Cerrar la aplicación
        System.exit(0);
    }

    private void closeCurrentWindow() {
        // 00148023 Obtener la ventana actual y cerrarla
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close(); // 00148023 Cerrar la ventana
    }
}
