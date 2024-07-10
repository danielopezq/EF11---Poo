package codigo.codigofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BancoApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 00148023 Cargar el archivo FXML del menu principal
        Parent root = FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
        // 00148023 Establecer el titulo de la ventana principal
        primaryStage.setTitle("Banco Nacional de Nlogonia");
        // 00148023 Crear y establecer la escena principal
        primaryStage.setScene(new Scene(root, 1268, 1000));
        // 00148023 Hacer la ventana no redimensionable
        primaryStage.setResizable(false);
        // 00148023 Mostrar la ventana principal
        primaryStage.show();
    }

    public static void main(String[] args) {
        // 00148023 Lanzar la aplicacion JavaFX
        launch(args);
    }
}
