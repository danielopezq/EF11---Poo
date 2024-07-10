package codigo.codigofinal;

import javafx.fxml.FXML; // 00043823 Importar la anotación FXML para los elementos de la interfaz gráfica
import javafx.fxml.FXMLLoader; // 00043823 Importar FXMLLoader para cargar archivos FXML
import javafx.scene.Parent; // 00043823 Importar la clase Parent para la escena raíz
import javafx.scene.Scene; // 00043823 Importar la clase Scene para crear la escena
import javafx.scene.control.Alert; // 00043823 Importar la clase Alert para mostrar alertas
import javafx.scene.control.Button; // 00043823 Importar la clase Button para los botones de la interfaz
import javafx.scene.control.TextField; // 00043823 Importar la clase TextField para los campos de texto de la interfaz
import javafx.scene.layout.StackPane; // 00043823 Importar la clase StackPane para el diseño de la interfaz
import javafx.stage.Stage; // 00043823 Importar la clase Stage para las ventanas de la interfaz

import java.io.BufferedWriter; // 00043823 Importar la clase BufferedWriter para escribir en archivos
import java.io.FileWriter; // 00043823 Importar la clase FileWriter para escribir en archivos
import java.io.IOException; // 00043823 Importar la clase IOException para manejar excepciones de entrada/salida
import java.sql.Connection; // 00043823 Importar la clase Connection para la conexión a la base de datos
import java.sql.PreparedStatement; // 00043823 Importar la clase PreparedStatement para ejecutar consultas SQL
import java.sql.ResultSet; // 00043823 Importar la clase ResultSet para manejar los resultados de las consultas SQL
import java.sql.SQLException; // 00043823 Importar la clase SQLException para manejar excepciones SQL
import java.time.LocalDate; // 00043823 Importar la clase LocalDate para manejar fechas
import java.time.LocalDateTime; // 00043823 Importar la clase LocalDateTime para manejar fechas y horas
import java.time.format.DateTimeFormatter; // 00043823 Importar la clase DateTimeFormatter para formatear fechas y horas
import java.util.ArrayList; // 00043823 Importar la clase ArrayList para manejar listas de datos
import java.util.List; // 00043823 Importar la clase List para manejar listas de datos

public class ReporteAController {
    private DatabaseConnection db; // 00043823 Objeto para manejar la conexión a la base de datos

    @FXML
    private TextField idClienteField; // 00043823 Campo de texto para ingresar el ID del cliente

    @FXML
    private TextField primerafechaField; // 00043823 Campo de texto para ingresar la primera fecha del rango

    @FXML
    private TextField segundafechaField; // 00043823 Campo de texto para ingresar la segunda fecha del rango

    @FXML
    private Button generarReporteButton; // 00043823 Botón para generar el reporte

    @FXML
    private StackPane rootPane; // 00043823 Panel raíz de la interfaz

    @FXML
    public void initialize() {
        // 00043823 Configurar la acción al presionar el botón de generar reporte
        generarReporteButton.setOnAction(e -> {
            String idCliente = idClienteField.getText(); // 00043823 Obtener el ID del cliente ingresado
            String primerafecha = primerafechaField.getText(); // 00043823 Obtener la primera fecha ingresada
            String segundafecha = segundafechaField.getText(); // 00043823 Obtener la segunda fecha ingresada
            // 00043823 Llamar al método para generar el reporte con los datos ingresados
            generarReporte(idCliente, primerafecha, segundafecha);
        });
    }

    private void generarReporte(String idCliente, String primerafecha, String segundafecha) {
        // 00043823 Formateador para validar las fechas ingresadas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            // 00043823 Validar la primera fecha ingresada
            LocalDate.parse(primerafecha, formatter);
            // 00043823 Validar la segunda fecha ingresada
            LocalDate.parse(segundafecha, formatter);
        } catch (Exception e) {
            // 00043823 Mostrar alerta si las fechas no tienen el formato correcto
            showAlert("Error de formato", "Por favor, ingrese las fechas en el formato correcto: yyyy-MM-dd");
            return; // 00043823 Salir del método si las fechas no son válidas
        }

        // 00043823 Obtener la lista de compras realizadas en el período especificado
        List<Compra> compras = comprasRealizadasEnPeriodo(idCliente, primerafecha, segundafecha);

        // 00043823 Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // 00043823 Formateador para el timestamp del nombre del archivo
        DateTimeFormatter formatterTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatterTimestamp); // 00043823 Formatear la fecha y hora actual

        // 00043823 Construir el contenido del reporte
        StringBuilder reportContent = new StringBuilder("Reporte A\n")
                .append("ID Cliente: ").append(idCliente).append("\n") // 00043823 Agregar el ID del cliente al reporte
                .append("Primera Fecha: ").append(primerafecha).append("\n") // 00043823 Agregar la primera fecha al reporte
                .append("Segunda Fecha: ").append(segundafecha).append("\n") // 00043823 Agregar la segunda fecha al reporte
                .append("Generado en: ").append(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n") // 00043823 Agregar la fecha y hora de generación
                .append("Detalle de Compras:\n"); // 00043823 Agregar el encabezado para el detalle de compras

        // 00043823 Iterar sobre las compras para agregar cada una al reporte
        for (Compra compra : compras) {
            reportContent.append("ID Compra: ").append(compra.getIdCompra()).append("\n") // 00043823 Agregar el ID de la compra
                    .append("Fecha Compra: ").append(compra.getFechaCompra()).append("\n") // 00043823 Agregar la fecha de la compra
                    .append("Monto Total: $").append(compra.getMontoTotal()).append("\n") // 00043823 Agregar el monto total de la compra
                    .append("Descripcion: ").append(compra.getDescripcion()).append("\n") // 00043823 Agregar la descripción de la compra
                    .append("------\n"); // 00043823 Agregar una separación entre compras
        }

        // 00043823 Obtener la ruta del escritorio del usuario
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        // 00043823 Crear el nombre del archivo del reporte
        String fileName = desktopPath + "/ReporteA" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) { // 00043823 Escribir el reporte en un archivo
            writer.write(reportContent.toString()); // 00043823 Escribir el contenido del reporte
            // 00043823 Mostrar alerta indicando que el reporte ha sido generado
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) { // 00043823 Manejo de excepciones de IO
            e.printStackTrace(); // 00043823 Imprimir el stack trace de la excepción
            // 00043823 Mostrar alerta indicando un error al generar el reporte
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados");
        }
    }

    private List<Compra> comprasRealizadasEnPeriodo(String idCliente, String primerafecha, String segundafecha) {
        List<Compra> compras = new ArrayList<>(); // 00043823 Lista para almacenar las compras

        // 00043823 Consulta SQL para obtener las compras realizadas en el período especificado
        String query = "SELECT c.id_compra, c.fecha_compra, c.monto_total, c.descripcion " +
                "FROM Compra c WHERE c.id_cliente = ? AND c.fecha_compra BETWEEN ? AND ? ORDER BY c.fecha_compra";

        try (Connection conn = db.getConnection(); // 00043823 Obtener la conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00043823 Preparar la consulta SQL

            stmt.setInt(1, Integer.parseInt(idCliente)); // 00043823 Establecer el ID del cliente en la consulta
            stmt.setString(2, primerafecha + " 00:00:00"); // 00043823 Establecer la primera fecha en la consulta
            stmt.setString(3, segundafecha + " 23:59:59"); // 00043823 Establecer la segunda fecha en la consulta

            ResultSet rs = stmt.executeQuery(); // 00043823 Ejecutar la consulta

            while (rs.next()) { // 00043823 Iterar sobre los resultados de la consulta
                int idCompra = rs.getInt("id_compra"); // 00043823 Obtener el ID de la compra
                String fechaCompra = rs.getString("fecha_compra"); // 00043823 Obtener la fecha de la compra
                double montoTotal = rs.getDouble("monto_total"); // 00043823 Obtener el monto total de la compra
                String descripcion = rs.getString("descripcion"); // 00043823 Obtener la descripción de la compra

                // 00043823 Añadir la compra a la lista
                compras.add(new Compra(idCompra, fechaCompra, montoTotal, descripcion));
            }

        } catch (SQLException e) { // 00043823 Manejo de excepciones SQL
            e.printStackTrace(); // 00043823 Imprimir el stack trace de la excepción
            // 00043823 Mostrar alerta indicando un error en la consulta SQL
            showAlert("Error SQL", "Ocurrió un error al ejecutar la consulta SQL:\n" + e.getMessage());
        }

        return compras; // 00043823 Retornar la lista de compras
    }

    private void showAlert(String title, String content) {
        // 00043823 Crear y mostrar una alerta de información
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // 00043823 Establecer el título de la alerta
        alert.setHeaderText(null); // 00043823 Establecer el encabezado de la alerta
        alert.setContentText(content); // 00043823 Establecer el contenido de la alerta
        alert.showAndWait(); // 00043823 Mostrar la alerta y esperar a que el usuario la cierre
    }

    @FXML
    public void returnToMainMenu() {
        try {
            // 00043823 Cargar el archivo FXML del menú principal
            Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
            // 00043823 Crear y mostrar una nueva escena del menú principal
            Stage stage = new Stage();
            stage.setTitle("Banco Nacional de Nlogonia"); // 00043823 Establecer el título de la ventana
            stage.setScene(new Scene(root, 1268, 1000)); // 00043823 Establecer la escena principal
            stage.setResizable(false); // 00043823 Hacer la ventana no redimensionable
            stage.show(); // 00043823 Mostrar la ventana principal
            closeCurrentWindow(); // 00043823 Cerrar la ventana actual
        } catch (IOException e) { // 00043823 Manejo de excepciones de IO
            e.printStackTrace(); // 00043823 Imprimir el stack trace de la excepción
        }
    }

    @FXML
    public void closeApplication() {
        System.exit(0); // 00043823 Cerrar la aplicación
    }

    private void closeCurrentWindow() {
        // 00043823 Obtener la ventana actual y cerrarla
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close(); // 00043823 Cerrar la ventana actual
    }
}
