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

public class ReporteDController {
    // 00148023 Conexion a la base de datos
    private DatabaseConnection db;

    @FXML
    private TextField facilitadorField; // 00148023 Campo de texto para el facilitador

    @FXML
    private Button generarReporteButton; // 00148023 Boton para generar el reporte

    @FXML
    private StackPane rootPane; // 00148023 Panel raiz de la interfaz

    @FXML
    public void initialize() {
        // 00148023 Configurar el evento de clic del boton de generar reporte
        generarReporteButton.setOnAction(e -> {
            String facilitador = facilitadorField.getText(); // 00148023 Obtener valor del campo facilitador
            // 00148023 Llamar al metodo generarReporte con el dato obtenido
            generarReporte(facilitador);
        });
    }

    private void generarReporte(String facilitador) {
        // 00148023 Calcular los clientes con el facilitador especificado
        String reporteContent = calcularClientesConFacilitador(facilitador);

        // 00148023 Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // 00148023 Formatear la fecha actual para el nombre del archivo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter); // 00148023 Convertir la fecha a string formateado

        // 00148023 Ruta del escritorio del usuario
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        // 00148023 Nombre del archivo de reporte
        String fileName = desktopPath + "/ReporteD_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // 00148023 Escribir el contenido del reporte en el archivo
            writer.write(reporteContent);
            // 00148023 Mostrar alerta de exito
            showAlert("Reporte Generado", "El reporte se ha generado y esta ubicado en el escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepcion de IO
            // 00148023 Mostrar alerta de error
            showAlert("Error", "Ocurrio un error al generar el reporte. Revise los datos ingresados: " + e.getMessage());
        }
    }

    private String calcularClientesConFacilitador(String facilitador) {
        // 00148023 Inicializar contenido del reporte
        StringBuilder reporteContent = new StringBuilder();

        // 00148023 Consulta SQL para obtener los clientes con el facilitador especificado
        String query = "SELECT c.id_cliente, c.nombre_completo, COUNT(DISTINCT p.id_compra) AS cantidad_compras, SUM(p.monto_total) AS total_gastado " +
                "FROM Cliente c " +
                "JOIN Compra p ON c.id_cliente = p.id_cliente " +
                "JOIN Tarjeta t ON p.id_tarjeta = t.id_tarjeta " +
                "WHERE t.facilitador = ? " +
                "GROUP BY c.id_cliente, c.nombre_completo";

        try (Connection conn = db.getConnection(); // 00148023 Obtener conexion a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL

            stmt.setString(1, facilitador); // 00148023 Establecer facilitador en la consulta

            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecutar la consulta y obtener el resultado

            while (rs.next()) {
                // 00148023 Obtener los datos del resultado de la consulta
                int idCliente = rs.getInt("id_cliente");
                String nombreCliente = rs.getString("nombre_completo");
                int cantidadCompras = rs.getInt("cantidad_compras");
                double totalGastado = rs.getDouble("total_gastado");

                // 00148023 Añadir los datos al contenido del reporte
                reporteContent.append("ID Cliente: ").append(idCliente).append("\n")
                        .append("Nombre Cliente: ").append(nombreCliente).append("\n")
                        .append("Cantidad de Compras: ").append(cantidadCompras).append("\n")
                        .append("Total Gastado: $").append(totalGastado).append("\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepcion SQL
        }

        return reporteContent.toString(); // 00148023 Retornar el contenido del reporte
    }

    private void showAlert(String title, String content) {
        // 00148023 Crear y mostrar una alerta de informacion
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // 00148023 Establecer el titulo de la alerta
        alert.setHeaderText(null); // 00148023 Establecer el encabezado de la alerta
        alert.setContentText(content); // 00148023 Establecer el contenido de la alerta
        alert.showAndWait(); // 00148023 Mostrar la alerta y esperar a que el usuario la cierre
    }

    @FXML
    public void returnToMainMenu() {
        try {
            // 00148023 Cargar el archivo FXML del menu principal
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            // 00148023 Crear y mostrar una nueva escena del menu principal
            Stage stage = new Stage();
            stage.setTitle("Banco Nacional de Nlogonia"); // 00148023 Establecer el titulo de la ventana
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establecer la escena principal
            stage.setResizable(false); // 00148023 Hacer la ventana no redimensionable
            stage.show(); // 00148023 Mostrar la ventana principal
            // 00148023 Cerrar la ventana actual
            closeCurrentWindow();
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepcion de IO
        }
    }

    @FXML
    public void closeApplication() {
        // 00148023 Cerrar la aplicacion
        System.exit(0);
    }

    private void closeCurrentWindow() {
        // 00148023 Obtener la ventana actual y cerrarla
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close(); // 00148023 Cerrar la ventana
    }
}
