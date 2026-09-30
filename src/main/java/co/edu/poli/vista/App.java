package co.edu.poli.vista;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación JavaFX para la gestión de drones.
 *
 * <p>
 * Esta clase se encarga de iniciar la aplicación, cargar la interfaz
 * definida mediante archivos FXML y configurar la ventana principal
 * del sistema.
 * </p>
 *
 * @author Jsyh
 * @version 1.0
 */
public class App extends Application {

    /**
     * Escena principal de la aplicación JavaFX.
     */
    private static Scene scene;

    /**
     * Inicia la aplicación JavaFX y configura la ventana principal.
     *
     * @param stage ventana principal proporcionada por JavaFX
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(
                loadFXML("/co/edu/poli/sw2/drone")
        );

        // Configurar el ícono de la aplicación
        stage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream(
                                "/co/edu/poli/sw2/img/logo.png"
                        )
                )
        );

        stage.setTitle("Gestion de Drones");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la raíz de la escena actual por otra interfaz FXML.
     *
     * @param fxml nombre y ruta del archivo FXML que se desea cargar
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    static void setRoot(String fxml) throws IOException {

        scene.setRoot(
                loadFXML(fxml)
        );
    }

    /**
     * Carga una interfaz gráfica desde un archivo FXML.
     *
     * @param fxml ruta del archivo FXML que se desea cargar
     * @return elemento raíz de la interfaz cargada
     * @throws IOException si ocurre un error durante la lectura
     *         o carga del archivo FXML
     */
    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader fxmlLoader =
                new FXMLLoader(
                        App.class.getResource(
                                fxml + ".fxml"
                        )
                );

        return fxmlLoader.load();
    }

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args argumentos proporcionados desde la línea de comandos
     */
    public static void main(String[] args) {

        launch();
    }
}