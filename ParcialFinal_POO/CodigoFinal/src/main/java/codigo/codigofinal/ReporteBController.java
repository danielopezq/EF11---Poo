package codigo.codigofinal; // 00148023 Paquete que contiene la clase ReporteBController.

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

public class ReporteBController { // 00148023 Declaracion de la clase ReporteBController.
    private DatabaseConnection db;

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private TextField idClienteField; // 00148023 Campo de texto para el ID del cliente.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private TextField mesField; // 00148023 Campo de texto para el mes.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private TextField anoField; // 00148023 Campo de texto para el año.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private Button generarReporteButton; // 00148023 Boton para generar el reporte.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private StackPane rootPane; // 00148023 Pane que actua como contenedor principal.

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void initialize() { // 00148023 Metodo que se ejecuta al inicializar el controlador.
        generarReporteButton.setOnAction(e -> { // 00148023 Define la accion del boton generarReporteButton.
            String idCliente = idClienteField.getText(); // 00148023 Obtiene el texto del campo idClienteField.
            String mes = mesField.getText(); // 00148023 Obtiene el texto del campo mesField.
            String ano = anoField.getText(); // 00148023 Obtiene el texto del campo anoField.
            generarReporte(idCliente, mes, ano); // 00148023 Llama al metodo generarReporte con los datos ingresados.
        });
    }

    private void generarReporte(String idCliente, String mes, String ano) { // 00148023 Metodo para generar el reporte.
        double totalGastado = calcularTotalGastado(idCliente, mes, ano); // 00148023 Calcula el total gastado.

        LocalDateTime now = LocalDateTime.now(); // 00148023 Obtiene la fecha y hora actuales.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); // 00148023 Define el formato de fecha y hora.
        String timestamp = now.format(formatter); // 00148023 Formatea la fecha y hora actuales.

        String reportContent = "Reporte B\n" + // 00148023 Contenido del reporte.
                "ID Cliente: " + idCliente + "\n" + // 00148023 Añade el ID del cliente al contenido.
                "Mes: " + mes + "\n" + // 00148023 Añade el mes al contenido.
                "Año: " + ano + "\n" + // 00148023 Añade el año al contenido.
                "Total Gastado: $" + totalGastado + "\n" + // 00148023 Añade el total gastado al contenido.
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n"; // 00148023 Añade la fecha y hora de generacion al contenido.

        String desktopPath = System.getProperty("user.home") + "/Desktop"; // 00148023 Obtiene la ruta del escritorio.
        String fileName = desktopPath + "/ReporteB" + timestamp + ".txt"; // 00148023 Define el nombre del archivo del reporte.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00148023 Crea un BufferedWriter para escribir el archivo.
            writer.write(reportContent); // 00148023 Escribe el contenido del reporte en el archivo.
            showAlert("Reporte Generado", "El reporte se ha generado y esta ubicado en su escritorio:\n" + fileName); // 00148023 Muestra una alerta indicando que el reporte se genero correctamente.
        } catch (IOException e) { // 00148023 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
            showAlert("Error", "Ocurrio un error al generar el reporte. Revise los datos ingresados: " + e.getMessage()); // 00148023 Muestra una alerta indicando que hubo un error al generar el reporte.
        }
    }

    private double calcularTotalGastado(String idCliente, String mes, String ano) { // 00148023 Metodo para calcular el total gastado.
        double totalGastado = 0.0; // 00148023 Inicializa el total gastado en 0.

        String query = "SELECT SUM(monto_total) AS total FROM Compra WHERE id_cliente = ? AND MONTH(fecha_compra) = ? AND YEAR(fecha_compra) = ?"; // 00148023 Consulta SQL para obtener el total gastado por el cliente en un mes y año especificos.

        try (Connection conn = db.getConnection(); // 00148023 Obtiene una conexion a la base de datos.
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Prepara la consulta SQL.

            stmt.setInt(1, Integer.parseInt(idCliente)); // 00148023 Establece el ID del cliente en la consulta.
            stmt.setInt(2, Integer.parseInt(mes)); // 00148023 Establece el mes en la consulta.
            stmt.setInt(3, Integer.parseInt(ano)); // 00148023 Establece el año en la consulta.

            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecuta la consulta SQL.

            if (rs.next()) { // 00148023 Si hay resultados.
                totalGastado = rs.getDouble("total"); // 00148023 Obtiene el total gastado del resultado.
            }

        } catch (SQLException e) { // 00148023 Captura excepciones SQL.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
        }

        return totalGastado; // 00148023 Retorna el total gastado.
    }

    private void showAlert(String title, String content) { // 00148023 Metodo para mostrar alertas.
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00148023 Crea una nueva alerta de tipo informacion.
        alert.setTitle(title); // 00148023 Establece el titulo de la alerta.
        alert.setHeaderText(null); // 00148023 Establece que la alerta no tiene encabezado.
        alert.setContentText(content); // 00148023 Establece el contenido de la alerta.
        alert.showAndWait(); // 00148023 Muestra la alerta y espera a que el usuario la cierre.
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
}
