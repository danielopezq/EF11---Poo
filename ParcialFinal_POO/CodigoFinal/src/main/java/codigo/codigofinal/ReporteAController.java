package codigo.codigofinal; // 00043823 Paquete donde se encuentra la clase ReporteAController.

import javafx.fxml.FXML; // 00043823 Importación la anotacion FXML.
import javafx.fxml.FXMLLoader; // 00043823 Importación de la clase FXMLLoader
import javafx.scene.Parent; // 00043823
import javafx.scene.Scene; // 00043823
import javafx.scene.control.Alert; // 00043823
import javafx.scene.control.Button; // 00043823
import javafx.scene.control.TextField; // 00043823
import javafx.scene.layout.StackPane; // 00043823
import javafx.stage.Stage; // 00043823

import java.io.BufferedWriter; // 00043823
import java.io.FileWriter; // 00043823
import java.io.IOException; // 00043823
import java.sql.Connection; // 00043823
import java.sql.PreparedStatement; // 00043823
import java.sql.ResultSet; // 00043823
import java.sql.SQLException; // 00043823
import java.time.LocalDateTime; // 00043823
import java.time.format.DateTimeFormatter; // 00043823
import java.util.ArrayList; // 00043823
import java.util.List; // 00043823

public class ReporteAController { // 00043823
    private DatabaseConnection db; // 00043823 Variable de tipo DatabaseConnection para poder acceder al metodo getConnection() de dicha clase.

    @FXML
    private TextField idClienteField; // 00043823

    @FXML
    private TextField primerafechaField; // 00043823

    @FXML
    private TextField segundafechaField; // 00043823

    @FXML
    private Button generarReporteButton; // 00043823

    @FXML // 00043823 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private StackPane rootPane; // 00043823 Pane que actua como contenedor principal.

    @FXML // 00043823
    public void initialize() { // 00043823
        generarReporteButton.setOnAction(e -> { // 00043823
            String idCliente = idClienteField.getText(); // 00043823
            String primerafecha = primerafechaField.getText(); // 00043823
            String segundafecha = segundafechaField.getText(); // 00043823
            generarReporte(idCliente, primerafecha, segundafecha); // 00043823
        });
    }

    private void generarReporte(String idCliente, String primerafecha, String segundafecha) { // 00043823
        List<Compra> compras = comprasRealizadasEnPeriodo(idCliente, primerafecha, segundafecha); // 00043823

        LocalDateTime now = LocalDateTime.now(); // 00043823
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); // 00043823
        String timestamp = now.format(formatter); // 00043823

        String reportContent = "Reporte A\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Primera Fecha: " + primerafecha + "\n" +
                "Segunda Fecha: " + segundafecha + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n\n" +
                "Detalle de Compras:\n"; // 00043823

        for (Compra compra : compras) {
            reportContent += "ID Compra: " + compra.getIdCompra() + "\n" +
                    "Fecha Compra: " + compra.getFechaCompra() + "\n" +
                    "Monto Total: $" + compra.getMontoTotal() + "\n" +
                    "Descripción: " + compra.getDescripcion() + "\n" +
                    "------\n"; // 00043823
        }

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteA" + timestamp + ".txt"; // 00043823
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00043823
            writer.write(reportContent.toString()); // 00043823
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName); // 00043823
        } catch (IOException e) { // 00043823
            e.printStackTrace(); // 00043823
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados"); // 00043823
        }
    }

    private List<Compra> comprasRealizadasEnPeriodo(String idCliente, String primerafecha, String segundafecha) { // 00043823
        List<Compra> compras = new ArrayList<>(); // 00043823

        String query = "SELECT id_compra, fecha_compra, monto_total, descripcion FROM Compra WHERE id_cliente = ? AND fecha_compra BETWEEN ? AND ?"; // 00043823

        try (Connection conn = db.getConnection(); // 00043823
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00043823

            stmt.setInt(1, Integer.parseInt(idCliente)); // 00043823
            stmt.setString(2, primerafecha); // 00043823
            stmt.setString(3, segundafecha); // 00043823

            ResultSet rs = stmt.executeQuery(); // 00043823

            while (rs.next()) { // 00043823
                int idCompra = rs.getInt("id_compra"); // 00043823
                String fechaCompra = rs.getString("fecha_compra"); // 00043823
                double montoTotal = rs.getDouble("monto_total"); // 00043823
                String descripcion = rs.getString("descripcion"); // 00043823

                compras.add(new Compra(idCompra, fechaCompra, montoTotal, descripcion)); // 00043823
            }

        } catch (SQLException e) { // 00043823
            e.printStackTrace(); // 00043823
        }

        return compras; // 00043823
    }

    private void showAlert(String title, String content) { // 00043823
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00043823
        alert.setTitle(title); // 00043823
        alert.setHeaderText(null); // 00043823
        alert.setContentText(content); // 00043823
        alert.showAndWait(); // 00043823
    }

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void returnToMainMenu() { // 00043823 Metodo para regresar al menu principal.
        try { // 00043823 Bloque try para manejar excepciones.
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml")); // 00043823 Carga el archivo FXML del menu principal.
            Stage stage = new Stage(); // 00043823 Crea una nueva ventana.
            stage.setTitle("Banco Nacional de Nlogonia"); // 00043823 Establece el titulo de la nueva ventana.
            stage.setScene(new Scene(root, 1268, 1000)); // 00043823 Establece la escena de la nueva ventana con sus dimensiones.
            stage.setResizable(false); // 00043823 Hace que la nueva ventana no sea redimensionable.
            stage.show(); // 00043823 Muestra la nueva ventana.
            closeCurrentWindow(); // 00043823 Llama al metodo para cerrar la ventana actual.
        } catch (IOException e) { // 00043823 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00043823 Imprime el stack trace de la excepcion.
        }
    }

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void closeApplication() { // 00043823 Metodo para cerrar la aplicacion.
        System.exit(0); // 00043823 Finaliza la aplicacion.
    }

    private void closeCurrentWindow() { // 00043823 Metodo para cerrar la ventana actual.
        Stage stage = (Stage) rootPane.getScene().getWindow(); // 00043823 Obtiene la ventana actual.
        stage.close(); // 00043823 Cierra la ventana actual.
    }
}