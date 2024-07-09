package codigo.codigofinal; // 00148023 Paquete que contiene la clase ReporteDController.

import javafx.fxml.FXML; // 00148023 Importacion de la anotacion FXML.
import javafx.fxml.FXMLLoader; // 00148023 Importacion de la clase FXMLLoader para cargar archivos FXML.
import javafx.scene.Parent; // 00148023 Importacion de la clase Parent que es el nodo raiz de la escena.
import javafx.scene.Scene; // 00148023 Importacion de la clase Scene que contiene la escena de JavaFX.
import javafx.scene.control.Alert; // 00148023 Importacion de la clase Alert para mostrar dialogos.
import javafx.scene.control.Button; // 00148023 Importacion de la clase Button para los botones de la interfaz.
import javafx.scene.control.TextField; // 00148023 Importacion de la clase TextField para los campos de texto.

import java.io.BufferedWriter; // 00148023 Importacion de la clase BufferedWriter para escribir archivos.
import java.io.FileWriter; // 00148023 Importacion de la clase FileWriter para escribir archivos.
import java.io.IOException; // 00148023 Importacion de la clase IOException para manejar excepciones de entrada/salida.
import java.sql.Connection; // 00148023 Importacion de la clase Connection para la conexion a la base de datos.
import java.sql.PreparedStatement; // 00148023 Importacion de la clase PreparedStatement para ejecutar sentencias SQL.
import java.sql.ResultSet; // 00148023 Importacion de la clase ResultSet para manejar los resultados de consultas SQL.
import java.sql.SQLException; // 00148023 Importacion de la clase SQLException para manejar excepciones SQL.
import java.time.LocalDateTime; // 00148023 Importacion de la clase LocalDateTime para manejar fechas y horas.
import java.time.format.DateTimeFormatter; // 00148023 Importacion de la clase DateTimeFormatter para formatear fechas y horas.

import javafx.scene.layout.StackPane; // 00148023 Importacion de la clase StackPane para el layout de la interfaz.
import javafx.stage.Stage; // 00148023 Importacion de la clase Stage que representa la ventana.

public class ReporteDController { // 00148023 Declaracion de la clase ReporteDController.
    private DatabaseConnection db;
    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private TextField facilitadorField; // 00148023 Campo de texto para el facilitador.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private Button generarReporteButton; // 00148023 Boton para generar el reporte.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private StackPane rootPane; // 00148023 Pane que actua como contenedor principal.

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void initialize() { // 00148023 Metodo que se ejecuta al inicializar el controlador.
        generarReporteButton.setOnAction(e -> { // 00148023 Define la accion del boton generarReporteButton.
            String facilitador = facilitadorField.getText(); // 00148023 Obtiene el texto del campo facilitadorField.
            generarReporte(facilitador); // 00148023 Llama al metodo generarReporte con el facilitador ingresado.
        });
    }

    private void generarReporte(String facilitador) { // 00148023 Metodo para generar el reporte.
        String reporteContent = calcularClientesConFacilitador(facilitador); // 00148023 Calcula el contenido del reporte.

        LocalDateTime now = LocalDateTime.now(); // 00148023 Obtiene la fecha y hora actuales.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); // 00148023 Define el formato de fecha y hora.
        String timestamp = now.format(formatter); // 00148023 Formatea la fecha y hora actuales.

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteD" + timestamp + ".txt"; // 00148023 Define el nombre del archivo del reporte.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00148023 Crea un BufferedWriter para escribir el archivo.
            writer.write(reporteContent); // 00148023 Escribe el contenido del reporte en el archivo.
            showAlert("Reporte Generado", "El reporte se ha generado y esta ubicado en su escritorio:\n" + fileName); // 00148023 Muestra una alerta indicando que el reporte se genero correctamente.
        } catch (IOException e) { // 00148023 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
            showAlert("Error", "Ocurrio un error al generar el reporte. Revise los datos ingresados"); // 00148023 Muestra una alerta indicando que hubo un error al generar el reporte.
        }
    }

    private String calcularClientesConFacilitador(String facilitador) { // 00148023 Metodo para calcular clientes con facilitador.
        StringBuilder reporteContent = new StringBuilder(); // 00148023 Crea un StringBuilder para el contenido del reporte.

        String query = "SELECT c.id_cliente, COUNT(*) AS cantidad_compras, SUM(monto_total) AS total_gastado " + // 00148023 Consulta SQL para obtener clientes con facilitador.
                "FROM Cliente c " + // 00148023 Union de tablas Cliente, Compra y Tarjeta.
                "JOIN Compra p ON c.id_cliente = p.id_cliente " + // 00148023 Union de tablas Cliente y Compra.
                "JOIN Tarjeta t ON p.id_tarjeta = t.id_tarjeta " + // 00148023 Union de tablas Compra y Tarjeta.
                "WHERE t.facilitador = ? " + // 00148023 Filtro por facilitador.
                "GROUP BY c.id_cliente"; // 00148023 Agrupacion por ID de cliente.

        try (Connection conn = db.getConnection(); // 00148023 Obtiene una conexion a la base de datos.
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Prepara la consulta SQL.

            stmt.setString(1, facilitador); // 00148023 Establece el facilitador en la consulta.

            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecuta la consulta SQL.

            while (rs.next()) { // 00148023 Recorre los resultados de la consulta.
                int idCliente = rs.getInt("id_cliente"); // 00148023 Obtiene el ID del cliente.
                int cantidadCompras = rs.getInt("cantidad_compras"); // 00148023 Obtiene la cantidad de compras.
                double totalGastado = rs.getDouble("total_gastado"); // 00148023 Obtiene el total gastado.

                reporteContent.append("ID Cliente: ").append(idCliente).append("\n") // 00148023 Añade el ID del cliente al contenido del reporte.
                        .append("Cantidad de Compras: ").append(cantidadCompras).append("\n") // 00148023 Añade la cantidad de compras al contenido del reporte.
                        .append("Total Gastado: $").append(totalGastado).append("\n\n"); // 00148023 Añade el total gastado al contenido del reporte.
            }

        } catch (SQLException e) { // 00148023 Captura excepciones SQL.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
        }

        return reporteContent.toString(); // 00148023 Retorna el contenido del reporte.
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void returnToMainMenu() { // 00148023 Metodo para regresar al menu principal.
        try { // 00148023 Bloque try para manejar excepciones.
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml")); // 00148023 Carga el archivo FXML del menu principal.
            Stage stage = new Stage(); // 00148023 Crea una nueva ventana.
            stage.setTitle("Banco Nacional de Nlogonia"); // 00148023 Establece el titulo de la nueva ventana.
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establece la escena de la nueva ventana con sus dimensiones.
            stage.setResizable(false); // 00148023 Hace que la nueva ventana no sea redimensionable.
            stage.show(); // 00148023 Muestra la nueva ventana.
            closeCurrentWindow(); // 00148023 Llama al metodo para cerrar la ventana actual.
        } catch (IOException e) { // 00148023 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
        }
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void closeApplication() { // 00148023 Metodo para cerrar la aplicacion.
        System.exit(0); // 00148023 Finaliza la aplicacion.
    }

    private void closeCurrentWindow() { // 00148023 Metodo para cerrar la ventana actual.
        Stage stage = (Stage) rootPane.getScene().getWindow(); // 00148023 Obtiene la ventana actual.
        stage.close(); // 00148023 Cierra la ventana actual.
    }

    private void showAlert(String title, String content) { // 00148023 Metodo para mostrar alertas.
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00148023 Crea una nueva alerta de tipo informacion.
        alert.setTitle(title); // 00148023 Establece el titulo de la alerta.
        alert.setHeaderText(null); // 00148023 Establece que la alerta no tiene encabezado.
        alert.setContentText(content); // 00148023 Establece el contenido de la alerta.
        alert.showAndWait(); // 00148023 Muestra la alerta y espera a que el usuario la cierre.
    }
}
