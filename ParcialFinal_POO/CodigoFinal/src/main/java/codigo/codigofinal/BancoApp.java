package codigo.codigofinal; // 00148023 Paquete que contiene la clase BancoApp.

import javafx.application.Application; // 00148023 Importacion de la clase Application de JavaFX.
import javafx.fxml.FXMLLoader; // 00148023 Importacion de la clase FXMLLoader para cargar archivos FXML.
import javafx.scene.Parent; // 00148023 Importacion de la clase Parent que es el nodo raiz de la escena.
import javafx.scene.Scene; // 00148023 Importacion de la clase Scene que contiene la escena de JavaFX.
import javafx.stage.Stage; // 00148023 Importacion de la clase Stage que representa la ventana.

public class BancoApp extends Application { // 00148023 Declaracion de la clase BancoApp que extiende Application.

    @Override // 00148023 Sobrescribir el metodo start de la clase Application.
    public void start(Stage primaryStage) throws Exception { // 00148023 Metodo que se ejecuta al iniciar la aplicacion.
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml")); // 00148023 Carga el archivo FXML que define la interfaz grafica.
        primaryStage.setTitle("Banco Nacional de Nlogonia"); // 00148023 Establece el titulo de la ventana principal.
        primaryStage.setScene(new Scene(root, 1268, 1000)); // 00148023 Establece la escena principal con sus dimensiones.
        primaryStage.setResizable(false); // 00148023 Hace que la ventana no sea redimensionable.
        primaryStage.show(); // 00148023 Muestra la ventana principal.
    }

    public static void main(String[] args) { // 00148023 Metodo principal que lanza la aplicacion.
        launch(args); // 00148023 Lanza la aplicacion JavaFX.
    }
}
