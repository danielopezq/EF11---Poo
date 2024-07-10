package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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

public class CompraOnlineController {
    // 00148023 Conexión a la base de datos
    private DatabaseConnection db;

    @FXML
    private TextField idClienteField; // 00148023 Campo de texto para el ID del cliente
    @FXML
    private TextField nombreField; // 00148023 Campo de texto para el nombre del cliente
    @FXML
    private TextField direccionField; // 00148023 Campo de texto para la dirección del cliente
    @FXML
    private TextField telefonoField; // 00148023 Campo de texto para el teléfono del cliente
    @FXML
    private TextField montoTotalField; // 00148023 Campo de texto para el monto total de la compra
    @FXML
    private TextField descripcionField; // 00148023 Campo de texto para la descripción de la compra
    @FXML
    private TextField tipoTarjetaField; // 00148023 Campo de texto para el tipo de tarjeta
    @FXML
    private TextField numeroTarjetaField; // 00148023 Campo de texto para el número de tarjeta
    @FXML
    private TextField facilitadorField; // 00148023 Campo de texto para el facilitador de la tarjeta
    @FXML
    private Button realizarCompraButton; // 00148023 Botón para realizar la compra
    @FXML
    private RadioButton nuevoClienteRadioButton; // 00148023 RadioButton para indicar si es un nuevo cliente
    @FXML
    private StackPane rootPane; // 00148023 Panel raíz de la interfaz

    @FXML
    public void initialize() {
        // 00148023 Configurar el evento de selección del RadioButton de nuevo cliente
        nuevoClienteRadioButton.setOnAction(e -> {
            // 00148023 Habilitar o deshabilitar campos según el estado del RadioButton
            if (nuevoClienteRadioButton.isSelected()) {
                idClienteField.setDisable(true); // 00148023 Deshabilitar campo de ID cliente
                nombreField.setDisable(false); // 00148023 Habilitar campo de nombre
                direccionField.setDisable(false); // 00148023 Habilitar campo de dirección
                telefonoField.setDisable(false); // 00148023 Habilitar campo de teléfono
            } else {
                idClienteField.setDisable(false); // 00148023 Habilitar campo de ID cliente
                nombreField.setDisable(true); // 00148023 Deshabilitar campo de nombre
                direccionField.setDisable(true); // 00148023 Deshabilitar campo de dirección
                telefonoField.setDisable(true); // 00148023 Deshabilitar campo de teléfono
            }
        });

        // 00148023 Configurar el evento de clic del botón de realizar compra
        realizarCompraButton.setOnAction(e -> {
            String idCliente = idClienteField.getText(); // 00148023 Obtener valor del campo ID cliente
            String nombre = nombreField.getText(); // 00148023 Obtener valor del campo nombre
            String direccion = direccionField.getText(); // 00148023 Obtener valor del campo dirección
            String telefono = telefonoField.getText(); // 00148023 Obtener valor del campo teléfono
            String montoTotal = montoTotalField.getText(); // 00148023 Obtener valor del campo monto total
            String descripcion = descripcionField.getText(); // 00148023 Obtener valor del campo descripción
            String tipoTarjeta = tipoTarjetaField.getText(); // 00148023 Obtener valor del campo tipo de tarjeta
            String numeroTarjeta = numeroTarjetaField.getText(); // 00148023 Obtener valor del campo número de tarjeta
            String facilitador = facilitadorField.getText(); // 00148023 Obtener valor del campo facilitador
            // 00148023 Llamar al método realizarCompra con los datos obtenidos
            realizarCompra(idCliente, nombre, direccion, telefono, montoTotal, descripcion, tipoTarjeta, numeroTarjeta, facilitador);
        });

        // 00148023 Inicializar el estado de los campos según el estado inicial del RadioButton
        if (!nuevoClienteRadioButton.isSelected()) {
            idClienteField.setDisable(false); // 00148023 Habilitar campo de ID cliente
            nombreField.setDisable(true); // 00148023 Deshabilitar campo de nombre
            direccionField.setDisable(true); // 00148023 Deshabilitar campo de dirección
            telefonoField.setDisable(true); // 00148023 Deshabilitar campo de teléfono
        }
    }

    private void realizarCompra(String idCliente, String nombre, String direccion, String telefono, String montoTotal, String descripcion, String tipoTarjeta, String numeroTarjeta, String facilitador) {
        // 00148023 Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // 00148023 Formatear la fecha de compra
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaCompra = now.format(formatter); // 00148023 Convertir la fecha a string formateado

        // 00148023 Determinar si es un nuevo cliente
        boolean nuevoCliente = idCliente.isEmpty();
        if (nuevoCliente) {
            // 00148023 Insertar un nuevo cliente y obtener su ID
            idCliente = insertarCliente(nombre, direccion, telefono);
        }

        // 00148023 Verificar si la tarjeta ya existe
        if (!tarjetaExiste(idCliente, numeroTarjeta)) {
            // 00148023 Insertar nueva tarjeta
            insertarTarjeta(idCliente, tipoTarjeta, numeroTarjeta, facilitador);
        } else {
            // 00148023 Actualizar tarjeta existente
            actualizarTarjeta(idCliente, tipoTarjeta, numeroTarjeta, facilitador);
        }

        // 00148023 Insertar la compra en la base de datos
        boolean compraExitosa = insertarCompra(idCliente, montoTotal, descripcion, fechaCompra, numeroTarjeta);
        if (compraExitosa) {
            // 00148023 Guardar el reporte de la compra
            guardarReporte(idCliente, montoTotal, descripcion, fechaCompra);
        }
    }

    private String insertarCliente(String nombre, String direccion, String telefono) {
        // 00148023 Consulta SQL para insertar un nuevo cliente
        String query = "INSERT INTO Cliente (nombre_completo, direccion, telefono) OUTPUT INSERTED.id_cliente VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL
            stmt.setString(1, nombre); // 00148023 Establecer nombre en la consulta
            stmt.setString(2, direccion); // 00148023 Establecer dirección en la consulta
            stmt.setString(3, telefono); // 00148023 Establecer teléfono en la consulta
            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecutar la consulta y obtener el resultado
            if (rs.next()) {
                // 00148023 Retornar el ID del nuevo cliente
                return String.valueOf(rs.getInt("id_cliente"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
            // 00148023 Mostrar alerta de error
            showAlert("Error", "Ocurrio un error al registrar el cliente. Revise los datos ingresados.");
        }
        return null; // 00148023 Retornar null si no se pudo insertar el cliente
    }

    private boolean tarjetaExiste(String idCliente, String numeroTarjeta) {
        // 00148023 Consulta SQL para verificar si la tarjeta ya existe
        String query = "SELECT COUNT(*) FROM Tarjeta WHERE id_cliente = ? AND numero_tarjeta = ?";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL
            stmt.setInt(1, Integer.parseInt(idCliente)); // 00148023 Establecer ID del cliente en la consulta
            stmt.setString(2, numeroTarjeta); // 00148023 Establecer número de tarjeta en la consulta
            ResultSet rs = stmt.executeQuery(); // 00148023 Ejecutar la consulta y obtener el resultado
            if (rs.next()) {
                // 00148023 Retornar verdadero si la tarjeta existe
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
        }
        return false; // 00148023 Retornar falso si la tarjeta no existe
    }

    private void insertarTarjeta(String idCliente, String tipoTarjeta, String numeroTarjeta, String facilitador) {
        // 00148023 Consulta SQL para insertar una nueva tarjeta
        String query = "INSERT INTO Tarjeta (id_cliente, numero_tarjeta, fecha_expiracion, tipo_tarjeta, facilitador) VALUES (?, ?, '2025-12-31', ?, ?)";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL
            stmt.setInt(1, Integer.parseInt(idCliente)); // 00148023 Establecer ID del cliente en la consulta
            stmt.setString(2, numeroTarjeta); // 00148023 Establecer número de tarjeta en la consulta
            stmt.setString(3, tipoTarjeta); // 00148023 Establecer tipo de tarjeta en la consulta
            stmt.setString(4, facilitador); // 00148023 Establecer facilitador en la consulta
            stmt.executeUpdate(); // 00148023 Ejecutar la consulta
        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
        }
    }

    private void actualizarTarjeta(String idCliente, String tipoTarjeta, String numeroTarjeta, String facilitador) {
        // 00148023 Consulta SQL para actualizar una tarjeta existente
        String query = "UPDATE Tarjeta SET tipo_tarjeta = ?, facilitador = ?, fecha_expiracion = '2025-12-31' WHERE id_cliente = ? AND numero_tarjeta = ?";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL
            stmt.setString(1, tipoTarjeta); // 00148023 Establecer tipo de tarjeta en la consulta
            stmt.setString(2, facilitador); // 00148023 Establecer facilitador en la consulta
            stmt.setInt(3, Integer.parseInt(idCliente)); // 00148023 Establecer ID del cliente en la consulta
            stmt.setString(4, numeroTarjeta); // 00148023 Establecer número de tarjeta en la consulta
            stmt.executeUpdate(); // 00148023 Ejecutar la consulta
        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
        }
    }

    private boolean insertarCompra(String idCliente, String montoTotal, String descripcion, String fechaCompra, String numeroTarjeta) {
        // 00148023 Consulta SQL para insertar una nueva compra
        String query = "INSERT INTO Compra (id_cliente, id_tarjeta, fecha_compra, monto_total, descripcion) VALUES (?, (SELECT id_tarjeta FROM Tarjeta WHERE numero_tarjeta = ?), ?, ?, ?)";
        try (Connection conn = db.getConnection(); // 00148023 Obtener conexión a la base de datos
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00148023 Preparar la consulta SQL
            stmt.setInt(1, Integer.parseInt(idCliente)); // 00148023 Establecer ID del cliente en la consulta
            stmt.setString(2, numeroTarjeta); // 00148023 Establecer número de tarjeta en la consulta
            stmt.setString(3, fechaCompra); // 00148023 Establecer fecha de compra en la consulta
            stmt.setDouble(4, Double.parseDouble(montoTotal)); // 00148023 Establecer monto total en la consulta
            stmt.setString(5, descripcion); // 00148023 Establecer descripción en la consulta
            stmt.executeUpdate(); // 00148023 Ejecutar la consulta
            // 00148023 Mostrar alerta de éxito
            showAlert("Compra Realizada", "La compra se ha realizado exitosamente.");
            return true; // 00148023 Retornar verdadero si la compra fue exitosa
        } catch (SQLException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción SQL
            // 00148023 Mostrar alerta de error
            showAlert("Error", "Ocurrio un error al realizar la compra. Revise los datos ingresados.");
            return false; // 00148023 Retornar falso si la compra falló
        }
    }

    private void guardarReporte(String idCliente, String montoTotal, String descripcion, String fechaCompra) {
        // 00148023 Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // 00148023 Formatear la fecha actual para el nombre del archivo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = now.format(formatter); // 00148023 Convertir la fecha a string formateado

        // 00148023 Contenido del reporte
        String reportContent = "Compra realizada\n" +
                "ID Cliente: " + idCliente + "\n" +
                "Monto Total: " + montoTotal + "\n" +
                "Descripcion: " + descripcion + "\n" +
                "Fecha de Compra: " + fechaCompra + "\n" +
                "Generado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";

        // 00148023 Ruta del escritorio del usuario
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        // 00148023 Nombre del archivo de reporte
        String fileName = desktopPath + "/Compra_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // 00148023 Escribir el contenido del reporte en el archivo
            writer.write(reportContent);
            // 00148023 Mostrar alerta de éxito
            showAlert("Reporte Generado", "El reporte de la compra se ha generado y esta ubicado en su escritorio:\n" + fileName);
        } catch (IOException e) {
            e.printStackTrace(); // 00148023 Manejo de excepción de IO
            // 00148023 Mostrar alerta de error
            showAlert("Error", "Ocurrio un error al generar el reporte. Revise los datos ingresados: " + e.getMessage());
        }
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
