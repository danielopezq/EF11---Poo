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
    private TextField idClienteField; // 00140323 Campo de texto para ingresar el ID del cliente

    @FXML
    private Button generarReporteButton; // 00140323 Botón para generar el reporte

    @FXML
    private StackPane rootPane; // 00140323 Panel raíz de la interfaz

    @FXML
    public void initialize() {
        // 00140323 Configurar la acción al presionar el botón de generar reporte
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText(); // 00140323 Obtener el ID del cliente ingresado
            generarReporte(idCliente); // 00140323 Llamar al método para generar el reporte
        });
    }

    private void generarReporte(String idCliente) {
        String nombreCliente = ""; // 00140323 Inicializar variable para almacenar el nombre del cliente
        StringBuilder tarjetasCredito = new StringBuilder(); // 00140323 Inicializar StringBuilder para tarjetas de crédito
        StringBuilder tarjetasDebito = new StringBuilder(); // 00140323 Inicializar StringBuilder para tarjetas de débito

        // 00140323 Configurar la URL de conexión a la base de datos
        String url = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433;databaseName=BCNN;encrypt=true;trustServerCertificate=true;";
        String user = "ParcialFinal"; // 00140323 Usuario de la base de datos
        String password = "parcialfinalpoo"; // 00140323 Contraseña del usuario de la base de datos

        // 00140323 Consulta SQL para obtener los datos de las tarjetas del cliente
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

        try (Connection conn = DriverManager.getConnection(url, user, password); // 00140323 Conectar a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00140323 Preparar la consulta SQL

            stmt.setInt(1, Integer.parseInt(idCliente)); // 00140323 Establecer el ID del cliente en la consulta

            ResultSet rs = stmt.executeQuery(); // 00140323 Ejecutar la consulta

            while (rs.next()) { // 00140323 Iterar sobre los resultados de la consulta
                nombreCliente = rs.getString("NombreCliente"); // 00140323 Obtener el nombre del cliente
                String tipoTarjeta = rs.getString("TipoTarjeta"); // 00140323 Obtener el tipo de tarjeta
                String numeroTarjeta = rs.getString("NumeroTarjeta"); // 00140323 Obtener el número de tarjeta

                if (tipoTarjeta.equals("Tarjetas de crédito")) { // 00140323 Verificar si es tarjeta de crédito
                    tarjetasCredito.append(numeroTarjeta).append("\n"); // 00140323 Añadir número de tarjeta de crédito
                } else if (tipoTarjeta.equals("Tarjetas de débito")) { // 00140323 Verificar si es tarjeta de débito
                    tarjetasDebito.append(numeroTarjeta).append("\n"); // 00140323 Añadir número de tarjeta de débito
                }
            }

        } catch (SQLException e) { // 00140323 Manejo de excepciones SQL
            e.printStackTrace(); // 00140323 Imprimir el stack trace de la excepción
        }

        LocalDateTime now = LocalDateTime.now(); // 00140323 Obtener la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); // 00140323 Formateador de fecha y hora
        String timestamp = now.format(formatter); // 00140323 Convertir la fecha y hora actual a string

        // 00140323 Contenido del reporte
        String reportContent = "Reporte C:\n" +
                "Tarjetas correspondientes al cliente " + nombreCliente + ":\n" +
                "Tarjetas de crédito:\n" +
                (tarjetasCredito.length() > 0 ? tarjetasCredito.toString() : "N/A\n") +
                "Tarjetas de débito:\n" +
                (tarjetasDebito.length() > 0 ? tarjetasDebito.toString() : "N/A\n") +
                "\nGenerado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        // 00140323 Nombre del archivo de reporte
        String fileName = System.getProperty("user.home") + "/Desktop/ReporteC_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00140323 Crear el archivo de reporte y escribir en él
            writer.write(reportContent); // 00140323 Escribir el contenido del reporte en el archivo
            // 00140323 Mostrar alerta indicando que el reporte ha sido generado
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) { // 00140323 Manejo de excepciones de IO
            e.printStackTrace(); // 00140323 Imprimir el stack trace de la excepción
            // 00140323 Mostrar alerta indicando un error al generar el reporte
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados.");
        }
    }

    private void showAlert(String title, String content) {
        // 00140323 Crear y mostrar una alerta de información
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // 00140323 Establecer el título de la alerta
        alert.setHeaderText(null); // 00140323 Establecer el encabezado de la alerta
        alert.setContentText(content); // 00140323 Establecer el contenido de la alerta
        alert.showAndWait(); // 00140323 Mostrar la alerta y esperar a que el usuario la cierre
    }

    @FXML
    public void returnToMainMenu() {
        try {
            // 00140323 Cargar el archivo FXML del menú principal
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            // 00140323 Crear y mostrar una nueva escena del menú principal
            Stage stage = new Stage();
            stage.setTitle("Banco Nacional de Nlogonia"); // 00140323 Establecer el título de la ventana
            stage.setScene(new Scene(root, 1268, 1000)); // 00140323 Establecer la escena principal
            stage.setResizable(false); // 00140323 Hacer la ventana no redimensionable
            stage.show(); // 00140323 Mostrar la ventana principal
            closeCurrentWindow(); // 00140323 Cerrar la ventana actual
        } catch (IOException e) { // 00140323 Manejo de excepciones de IO
            e.printStackTrace(); // 00140323 Imprimir el stack trace de la excepción
        }
    }

    @FXML
    public void closeApplication() {
        System.exit(0); // 00140323 Cerrar la aplicación
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) rootPane.getScene().getWindow(); // 00140323 Obtener la ventana actual
        stage.close(); // 00140323 Cerrar la ventana actual
    }
}
