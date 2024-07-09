package codigo.codigofinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

public class ReporteCController {
    private DatabaseConnection db;

    @FXML
    private TextField idClienteField; //00140323 Sirve para generar un espacio de texto donde se escribira el ID del cliente.

    @FXML
    private Button generarReporteButton; //00140323 Genera un boton que al darle clic generara el reporte.

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> {  //00140323 Es el evento que ocurre al hacer clic.
            String idCliente = idClienteField.getText();  //00140323 Sirve para obtener el id del cliente que fue ingresado.
            generarReporte(idCliente);  //00140323 Sirve para llamar al metodo generarReporte con el id del cliente como parametro.
        });
    }

    private void generarReporte(String idCliente) {
        String nombreCliente = "";  //00140323 Es la variable correspondiente al nombre del cliente de la base de datos.
        StringBuilder tarjetasCredito = new StringBuilder();  //00140323 Sirve para almacenar las tarjetas de credito del cliente.
        StringBuilder tarjetasDebito = new StringBuilder();  //00140323 Sirve para almacenar las tarjetas de debito del cliente.

        String query = "DECLARE @ClienteID INT = ?; " +  //00140323 Esta es la consulta que se encarga de obtener las tarjetas en el formato que requeria el reporte C de la guia.
                "SELECT " +
                "    c.nombre_completo AS NombreCliente, " +
                "    CASE " +
                "        WHEN t.tipo_tarjeta = 'Crédito' THEN 'Tarjetas de crédito' " +
                "        WHEN t.tipo_tarjeta = 'Débito' THEN 'Tarjetas de débito' " +
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

        try (Connection conn = db.getConnection();  //00140323 Sirve para conectarse a la base de datos.
             PreparedStatement stmt = conn.prepareStatement(query)) {  //00140323 Sirve para preparar la consulta SQL.

            stmt.setInt(1, Integer.parseInt(idCliente));  //00140323 Sirve para poner el parametro, que es idCliente, en la consulta.

            ResultSet rs = stmt.executeQuery();  //00140323 Sirve para ejecutar la consulta.

            while (rs.next()) {
                nombreCliente = rs.getString("NombreCliente");  //00140323 Sirve para obtener el nombre del cliente completo.
                String tipoTarjeta = rs.getString("TipoTarjeta");  //00140323 Sirve para obtener el tipo de la tarjeta, ya sea credito o debito.
                String numeroTarjeta = rs.getString("NumeroTarjeta");  //00140323 Sirve para obtener el numero de la tarjeta.

                if (tipoTarjeta.equals("Tarjetas de crédito")) {  //00140323 Aca vemos si la tarjeta es de credito.
                    tarjetasCredito.append(numeroTarjeta).append("\n");  //00140323 Se almacena la tarjeta de credito.
                } else if (tipoTarjeta.equals("Tarjetas de débito")) {  //00140323 Aca vemos si la tarjeta es de debito.
                    tarjetasDebito.append(numeroTarjeta).append("\n");  //00140323 Se almacena la tarjea de debito.
                }
            }

        } catch (SQLException e) {  //00140323 Sirve para administrar excepciones sobre la base de datos.
            e.printStackTrace();  //00140323 Se le muestra al usuario la exepcion.
        }

        LocalDateTime now = LocalDateTime.now();  //00140323 Sirve para obtener a fecha actual exacta.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");  //00140323 Aqui se define el formato de fecha y hora.
        String timestamp = now.format(formatter);  //00140323 Pone la fecha y hora actual en el formato que usamos.

        String reportContent = "Reporte C:\n" +
                "Tarjetas correspondientes al cliente " + nombreCliente + ":\n" +  //00140323 Aca vemos lo que se imprimira en el reporteC.txt
                "Tarjetas de crédito:\n" +
                (tarjetasCredito.length() > 0 ? tarjetasCredito.toString() : "N/A\n") +  //00140323 Muestra las tarjetas de credito relacionadas al cliente, si no hay imprime N/A.
                "Tarjetas de débito:\n" +
                (tarjetasDebito.length() > 0 ? tarjetasDebito.toString() : "N/A\n") +  //00140323 Muestra las tarjetas de debito relacionadas al cliente, si no hay imprime N/A.
                "\nGenerado en: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n";  //00140323 Le muestra al usuario la fecha del momento en que se genero el reporte.

        String fileName = System.getProperty("user.home") + "/Desktop/ReporteC_" + timestamp + ".txt";  //00140323 Le asignamos el nombre y la ruta al archivo de texto del reporte.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {  //00140323 Sirve para crear un escritor del archivo de texto del reporte.
            writer.write(reportContent);  //00140323 Sirve para escribir el contenido del reporte dentro del archivo de texto.
            showAlert("Reporte Generado", "El reporte se ha generado y está ubicado en su escritorio:\n" + fileName);  //00140323 Se le indica al usuario un mensaje de que la generacion del reporte fue exitosa.
        } catch (IOException e) {  //00140323 Sirve para administrar excepciones sobre la escritura del archivo de texto del reporte.
            e.printStackTrace();  //00140323 Se le muestra al usuario la excepcion.
            showAlert("Error", "Ocurrió un error al generar el reporte. Revise los datos ingresados");  //00140323 Se le indica al usuario que hubo un error en la generacion del reporte.
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  //00140323 Sirve para crear una alerta informativa.
        alert.setTitle(title);  //00140323 Aqui se establece el titulo de la alerta.
        alert.setHeaderText(null);  //00140323 Aqui se establece el encabezado de la alerta como nulo.
        alert.setContentText(content);  //00140323 Aqui se establece el mensaje de la alerta.
        alert.showAndWait();  //00140323 Aqui se le muestra la alerta al usuario.
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



