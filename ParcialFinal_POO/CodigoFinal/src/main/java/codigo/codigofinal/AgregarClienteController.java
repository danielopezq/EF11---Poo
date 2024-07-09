package codigo.codigofinal; // 00043823 Paquete donde se encuentra la clase ReporteAController.

import javafx.fxml.FXML; // 00043823 Importación la anotacion FXML.
import javafx.fxml.FXMLLoader; // 00043823 Importación de la clase FXMLLoader para poder cargar todos los archivos FXML.
import javafx.scene.Parent; // 00043823 Importación de la clase Parent para obtener el nodo de la raiz de la escena.
import javafx.scene.Scene; // 00043823 Importación de la clase Scene para las escenas de JavaFX.
import javafx.scene.control.Alert; // 00043823 Importación de la clase Alert para poder crear y mostrar una alerta.
import javafx.scene.control.Button; // 00043823 Importación de la clase Button para los botones.
import javafx.scene.control.TextField; // 00043823 Importación de la clase TextField para los campos de texto.
import javafx.scene.layout.StackPane; // 00043823 Importación de la clase StackPane
import javafx.stage.Stage; // 00043823 Importación de la clase Stage para las ventanas.

import java.io.BufferedWriter; // 00043823 Importación de la clase BufferedWriter para poder dentro del archivo lo que se ha guardado en el buffer.
import java.io.FileWriter; // 00043823 Importación de la clase FileWriter para poder escribir dentro de los archivos.
import java.io.IOException; // 00043823 Importación de la clase IOException para poder manejar las excepciones de entrada/salida.
import java.sql.Connection; // 00043823 Importación de la clase Connection para poder hacer la conexion con la BD.
import java.sql.PreparedStatement; // 00043823 Importación de la clase PreparedStatement para poder ejecutar la consulta SQL.
import java.sql.ResultSet; // 00043823 Importación de la clase ResultSet para poder manejar los resultados de la consulta SQL.
import java.sql.SQLException; // 00043823 Importación de la clase SQLEsception para poder manejar las excepciones que puedan suceder al conectarse a la BD.
import java.time.LocalDateTime; // 00043823 Importación de la clase LocalDateTime para poder manejar fechas y horas.
import java.time.format.DateTimeFormatter; // 00043823 Importación de la clase DateTimeFormatter para poder formatear fechas y horas.
import java.util.ArrayList; // 00043823 Importación de la clase ArrayList para poder hacer que la lista de las compras sea un arreglo
import java.util.List; // 00043823 Importación de la clase List para crear una lista y poder mandar la lista al archivo txt.


public class AgregarClienteController {
    private DatabaseConnection db; // 00043823 Variable de tipo DatabaseConnection para poder acceder al metodo getConnection() de dicha clase.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    private TextField idClienteField; // 00043823 Campo de texto para el idCliente.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    private TextField nombreField; // 00043823 Campo de texto para el nombre.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    private TextField direccionField; // 00043823 Campo de texto para la direccion.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    private TextField telefonoField; // 00043823 Campo de texto para el telefono.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    private Button agregarBDButton; // 00043823 Boton para generar el reporte.

    @FXML // 00043823 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private StackPane rootPane; // 00043823 Pane que actua como contenedor principal.

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void initialize() { // 00043823 Metodo que se ejecuta cuando se se inicializa elc ontrolador.
        agregarBDButton.setOnAction(e -> { // 00043823 Se define la accion de generarReporteButton.
            int idCliente = Integer.parseInt(idClienteField.getText()); // 00043823 Se crea y se inicializa una variable de tipo String idCliente que guarda lo que se ha digitado en el TextField.
            String nombre = nombreField.getText(); // 00043823 Se crea y se inicializa una variable de tipo String nombre que guarda lo que se ha digitado en el TextField.
            String direccion = direccionField.getText(); // 00043823 Se crea y se inicializa una variable de tipo String direccion que guarda lo que se ha digitado en el TextField.
            String telefono = telefonoField.getText(); // 00043823 Se crea y se inicializa una variable de tipo String telefonp que guarda lo que se ha digitado en el TextField.
            agregarBD(idCliente, nombre, direccion, telefono); // 00043823 Se utiliza el metodo agregarBD y se le pasa como parametro las variables antes inicializadas.
        });
    }


    private void agregarBD(int idCliente, String nombre, String direccion, String telefono) { // 00043823 Metodo agregarBD con los parametros que el usuario introducirá en la ventana.

        String query = "INSERT INTO Clientes(id_cliente, nombre_completo, direccion, telefono) VALUES("+ idCliente + ", " + nombre + ", "+ direccion+", " + telefono + ")"; // 00043823 Se define la consulta que nos dará el resultado que queremos, así como en SQL Server.

        try (Connection conn = db.getConnection(); // 00043823 Se inicia el try y se crea la variable de tipo Connection que tendrá el método getConnection() de la clase DatabaseConnection.
             PreparedStatement stmt = conn.prepareStatement(query)) { // 00043823 Prepara la consulta SQL.

            ResultSet rs = stmt.executeQuery(); // 00043823 Se ejecuta la consulta SQL.

           showAlert("Base de datos", "Los valores se han agregado exitosamente a la base de datos.");
        } catch (SQLException e) { // 00043823 Captura excepciones de tipo SQL.
            e.printStackTrace(); // 00043823 Imprime el stack trace de la excepcion en dado caso falle lo que está en el try.
            showAlert("Error", "Los valores no se han agregado a la base de datos.");
        }
    }

    private void showAlert(String title, String content) { // 00043823 Metodo showAlert para mostrar un mensaje en el momento que se utilice.
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // 00043823 Se crea la variable de tipo Alert y se define el tipo de Alert que será.
        alert.setTitle(title); // 00043823 Se le da un titulo al Alert.
        alert.setHeaderText(null); // 00043823 Se le indica que el Alert no tendrá encabezado.
        alert.setContentText(content); // 00043823 Se le define el texto que mostrará el Alert.
        alert.showAndWait(); // 00043823 Muestra el Alert hasta que el usuario lo cierre.
    }

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void returnToMainMenu() { // 00043823 Metodo returnToMainMenu para regresar al menu principal.
        try { // 00043823 Inicio del bloque try para manejar excepciones.
            Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml")); // 00043823 Carga el archivo FXML del menu principal.
            Stage stage = new Stage(); // 00043823 Crea una nueva ventana y se guarda en la variable stage.
            stage.setTitle("Banco Nacional de Nlogonia"); // 00043823 Establece el titulo a la ventana.
            stage.setScene(new Scene(root, 1268, 1000)); // 00043823 Establece la escena de la ventana y se le definen las dimensiones.
            stage.setResizable(false); // 00043823 Hace que la nueva ventana no sea redimensionable.
            stage.show(); // 00043823 Muestra la nueva ventana.
            closeCurrentWindow(); // 00043823 Llama al metodo para cerrar la ventana actual.
        } catch (IOException e) { // 00043823 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00043823 Imprime el stack trace de la excepcion en dado caso falle lo que está en el try.
        }
    }

    @FXML // 00043823 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void closeApplication() { // 00043823 Metodo closeApplication para cerrar la aplicacion.
        System.exit(0); // 00043823 Finaliza la aplicacion al mandar como parametro 0 en el System.exit().
    }

    private void closeCurrentWindow() { // 00043823 Metodo closeCurrentWindow para cerrar la ventana actual.
        Stage stage = (Stage) rootPane.getScene().getWindow(); // 00043823 Obtiene la ventana actual y se guarda en la variable stage de tipo Stage.
        stage.close(); // 00043823 Cierra la ventana actual, cerrando la variable stage.
    }
}
