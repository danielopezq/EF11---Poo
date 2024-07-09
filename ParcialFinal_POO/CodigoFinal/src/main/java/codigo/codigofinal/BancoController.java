package codigo.codigofinal; // 00148023 Paquete que contiene la clase BancoController.

import javafx.fxml.FXML; // 00148023 Importacion de la anotacion FXML.
import javafx.fxml.FXMLLoader; // 00148023 Importacion de la clase FXMLLoader para cargar archivos FXML.
import javafx.scene.Parent; // 00148023 Importacion de la clase Parent que es el nodo raiz de la escena.
import javafx.scene.Scene; // 00148023 Importacion de la clase Scene que contiene la escena de JavaFX.
import javafx.stage.Stage; // 00148023 Importacion de la clase Stage que representa la ventana.
import javafx.scene.layout.StackPane; // 00148023 Importacion de la clase StackPane para el layout de la interfaz.

import java.io.IOException; // 00148023 Importacion de la clase IOException para manejar excepciones de entrada/salida.

public class BancoController { // 00148023 Declaracion de la clase BancoController.

    @FXML // 00148023 Anotacion para indicar que el siguiente elemento es inyectado por FXML.
    private StackPane rootPane; // 00148023 Pane que actua como contenedor principal.

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void handleReporteA() { // 00148023 Metodo que maneja el evento para el reporte A.
        closeCurrentWindow(); // 00148023 Llama al metodo para cerrar la ventana actual.
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void handleReporteB() { // 00148023 Metodo que maneja el evento para el reporte B.
        try { // 00148023 Bloque try para manejar excepciones.
            Parent root = FXMLLoader.load(getClass().getResource("ReporteB.fxml")); // 00148023 Carga el archivo FXML para el reporte B.
            Stage stage = new Stage(); // 00148023 Crea una nueva ventana.
            stage.setTitle("Generar Reporte B"); // 00148023 Establece el titulo de la nueva ventana.
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establece la escena de la nueva ventana con sus dimensiones.
            stage.setResizable(false); // 00148023 Hace que la nueva ventana no sea redimensionable.
            stage.show(); // 00148023 Muestra la nueva ventana.
            closeCurrentWindow(); // 00148023 Llama al metodo para cerrar la ventana actual.
        } catch (IOException e) { // 00148023 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
        }
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void handleReporteC() { // 00148023 Metodo que maneja el evento para el reporte C.
        closeCurrentWindow(); // 00148023 Llama al metodo para cerrar la ventana actual.
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void handleReporteD() { // 00148023 Metodo que maneja el evento para el reporte D.
        try { // 00148023 Bloque try para manejar excepciones.
            Parent root = FXMLLoader.load(getClass().getResource("ReporteD.fxml")); // 00148023 Carga el archivo FXML para el reporte D.
            Stage stage = new Stage(); // 00148023 Crea una nueva ventana.
            stage.setTitle("Generar Reporte D"); // 00148023 Establece el titulo de la nueva ventana.
            stage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establece la escena de la nueva ventana con sus dimensiones.
            stage.setResizable(false); // 00148023 Hace que la nueva ventana no sea redimensionable.
            stage.show(); // 00148023 Muestra la nueva ventana.
            closeCurrentWindow(); // 00148023 Llama al metodo para cerrar la ventana actual.
        } catch (IOException e) { // 00148023 Captura excepciones de entrada/salida.
            e.printStackTrace(); // 00148023 Imprime el stack trace de la excepcion.
        }
    }

    @FXML // 00148023 Anotacion para indicar que el siguiente metodo es un controlador de eventos FXML.
    public void closeApplication() { // 00148023 Metodo que maneja el evento para cerrar la aplicacion.
        System.exit(0); // 00148023 Finaliza la aplicacion.
    }

    private void closeCurrentWindow() { // 00148023 Metodo para cerrar la ventana actual.
        Stage stage = (Stage) rootPane.getScene().getWindow(); // 00148023 Obtiene la ventana actual.
        stage.close(); // 00148023 Cierra la ventana actual.
    }
}
