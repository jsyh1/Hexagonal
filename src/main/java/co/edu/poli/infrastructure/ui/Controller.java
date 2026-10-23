package co.edu.poli.infrastructure.ui;

import java.io.IOException;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ActualizarDroneUseCase;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;
import co.edu.poli.aplication.port.in.CrearDroneUseCase;
import co.edu.poli.aplication.port.in.EliminarDroneUseCase;
import co.edu.poli.aplication.port.in.EvaluarViabilidadVueloUseCase;
import co.edu.poli.aplication.port.in.ListarDronesUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;
import co.edu.poli.aplication.port.out.GeocodingPort;
import co.edu.poli.aplication.port.out.WeatherApiPort;
import co.edu.poli.aplication.service.ActualizarDroneService;
import co.edu.poli.aplication.service.ConsultarClimaService;
import co.edu.poli.aplication.service.CrearDroneService;
import co.edu.poli.aplication.service.EliminarDroneService;
import co.edu.poli.aplication.service.EvaluarViabilidadVueloService;
import co.edu.poli.aplication.service.ListarDronesService;
import co.edu.poli.aplication.weather.WeatherNotificationService;
import co.edu.poli.infrastructure.persistence.MysqlDroneRepository;
import co.edu.poli.infrastructure.weather.OpenMeteoGeocodingAdapter;
import co.edu.poli.infrastructure.weather.OpenMeteoWeatherAdapter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/** Controlador de la interfaz JavaFX de gestión de drones y clima. */
public class Controller {

    /** Crea el controlador que conecta la interfaz con los casos de uso. */
    public Controller() {
    }

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSerial;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPeso;

    @FXML
    private ChoiceBox<String> cbTipo;

    @FXML
    private ChoiceBox<String> cbCiudad;

    @FXML
    private TableView<Drone> tblDrones;

    @FXML
    private TableColumn<Drone, Integer> colId;

    @FXML
    private TableColumn<Drone, String> colSenal;

    @FXML
    private TableColumn<Drone, String> colModelo;

    @FXML
    private TableColumn<Drone, Double> colPeso;

    private final DroneRepository droneRepository =
            new MysqlDroneRepository();

    private final CrearDroneUseCase crearDrone =
            new CrearDroneService(droneRepository);

    private final ActualizarDroneUseCase actualizarDrone =
            new ActualizarDroneService(droneRepository);

    private final EliminarDroneUseCase eliminarDrone =
            new EliminarDroneService(droneRepository);

    private final ListarDronesUseCase listarDrones =
            new ListarDronesService(droneRepository);

    private final ConsultarClimaUseCase consultarClima =
            crearConsultarClimaService();

    // Servicio que administra las suscripciones meteorológicas.
    private final WeatherNotificationService weatherService =
            crearWeatherNotificationService();

    /** Crea el servicio que administra las suscripciones a notificaciones. */
    private WeatherNotificationService crearWeatherNotificationService() {
        return new WeatherNotificationService(consultarClima);
    }

    /** Configura los adaptadores de geocodificación y consulta meteorológica. */
    private ConsultarClimaUseCase crearConsultarClimaService() {
        GeocodingPort geocoding =
                new OpenMeteoGeocodingAdapter();

        WeatherApiPort weatherApi =
                new OpenMeteoWeatherAdapter();

        return new ConsultarClimaService(geocoding, weatherApi);
    }

    /** Inicializa controles, ciudades disponibles y datos de la tabla. */
    @FXML
    private void initialize() {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colSenal.setCellValueFactory(
                new PropertyValueFactory<>("serial"));

        colModelo.setCellValueFactory(
                new PropertyValueFactory<>("modelo"));

        colPeso.setCellValueFactory(
                new PropertyValueFactory<>("peso"));

        txtId.setEditable(false);

        if (!cbTipo.getItems().contains("Seleccionar")) {
            cbTipo.getItems().add(0, "Seleccionar");
        }

        cbTipo.setValue("Seleccionar");

        // Ciudades precargadas para consultar el clima.
        cbCiudad.setItems(FXCollections.observableArrayList(
                "Bogotá",
                "Medellín",
                "Cali",
                "Barranquilla",
                "Cartagena",
                "Bucaramanga",
                "Pereira",
                "Santa Marta",
                "Manizales",
                "Pasto"
        ));

        cbCiudad.setValue("Bogotá");

        tblDrones.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) ->
                        mostrarDrone(seleccionado));

        cargarDrones();
    }

    /** Limpia el formulario para iniciar el registro de un drone. */
    @FXML
    private void nuevoDron() {
        limpiarFormulario();
        tblDrones.getSelectionModel().clearSelection();
    }

    /** Registra el drone descrito en el formulario. */
    @FXML
    private void guardarDron() {
        try {
            crearDrone.crearDrone(leerDrone(0));
            cargarDrones();
            limpiarFormulario();
        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo guardar el drone", exception);
        }
    }

    /** Actualiza el drone seleccionado con los datos del formulario. */
    @FXML
    private void editarDron() {
        try {
            int id = leerId();

            Drone actualizado =
                    actualizarDrone.actualizarDrone(id, leerDrone(id));

            if (actualizado == null) {
                mostrarError(
                        "No se encontró el drone",
                        "Verifica el ID seleccionado.");
                return;
            }

            cargarDrones();
            limpiarFormulario();

        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo actualizar el drone", exception);
        }
    }

    /** Elimina el drone seleccionado y lo da de baja de las notificaciones. */
    @FXML
    private void eliminarDron() {
        try {
            Drone seleccionado =
                    tblDrones.getSelectionModel().getSelectedItem();

            int id = leerId();

            eliminarDrone.eliminarDrone(id);

            // Evitar mantener suscrito un drone eliminado.
            if (seleccionado != null) {
                weatherService.darDeBaja(seleccionado);
            }

            cargarDrones();
            limpiarFormulario();

        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo eliminar el drone", exception);
        }
    }

    /** Suscribe el drone seleccionado a las notificaciones meteorológicas. */
    @FXML
    private void suscribirDrone() {
        Drone seleccionado =
                tblDrones.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError(
                    "Suscripción meteorológica",
                    "Selecciona un drone de la tabla.");
            return;
        }

        weatherService.suscribir(seleccionado);

        mostrarInformacion(
                "Suscripción realizada",
                "El drone " + seleccionado.getSerial()
                + " recibirá las notificaciones meteorológicas.");
    }

    /** Cancela la suscripción meteorológica del drone seleccionado. */
    @FXML
    private void desuscribirDrone() {
        Drone seleccionado =
                tblDrones.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError(
                    "Suscripción meteorológica",
                    "Selecciona un drone de la tabla.");
            return;
        }

        weatherService.darDeBaja(seleccionado);

        mostrarInformacion(
                "Suscripción eliminada",
                "El drone " + seleccionado.getSerial()
                + " ya no recibirá notificaciones meteorológicas.");
    }

    /** Consulta el clima, evalúa la viabilidad y notifica a los drones. */
    @FXML
    private void notificarDrones() {
        String ciudad = cbCiudad.getValue();

        if (ciudad == null || ciudad.isBlank()) {
            mostrarError(
                    "Consulta meteorológica",
                    "Selecciona una ciudad.");
            return;
        }

        try {
            var clima = consultarClima.consultarClima(ciudad);
            String resultado = evaluarViabilidad.evaluarViabilidad(ciudad, clima);
            weatherService.notificar(clima, resultado);

            mostrarInformacion(
                    "Viabilidad de vuelo en " + ciudad,
                    resultado + "\n\nSe notificó a los drones suscritos.");

        } catch (IOException exception) {
            mostrarError(
                    "Error al consultar las API",
                    exception.getMessage());

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            mostrarError(
                    "Consulta interrumpida",
                    "La consulta meteorológica fue interrumpida.");

        } catch (Exception exception) {
            mostrarError(
                    "Error al consultar el clima",
                    exception.getMessage());
        }
    }

    /** Carga en la tabla los drones almacenados. */
    private void cargarDrones() {
        try {
            tblDrones.setItems(
                    FXCollections.observableArrayList(
                            listarDrones.listarDrones()));

        } catch (IllegalStateException exception) {
            mostrarError("No se pudieron cargar los drones", exception);
        }
    }

    /**
     * Construye un drone con los valores actuales del formulario.
     *
     * @param id identificador asignado al drone
     * @return drone con los datos del formulario
     * @throws IllegalArgumentException si los campos están vacíos o el peso
     *         no es válido
     */
    private Drone leerDrone(int id) {
        String serial = txtSerial.getText().trim();
        String modelo = txtModelo.getText().trim();
        String pesoTexto = txtPeso.getText().trim();

        if (serial.isEmpty() || modelo.isEmpty() || pesoTexto.isEmpty()) {
            throw new IllegalArgumentException(
                    "Completa serial, modelo y peso.");
        }

        try {
            double peso = Double.parseDouble(pesoTexto);
            return new Drone(id, serial, modelo, peso);

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "El peso debe ser un número válido.", exception);
        }
    }

    /**
     * Lee el identificador del formulario.
     *
     * @return identificador ingresado
     * @throws IllegalArgumentException si el campo no contiene un ID válido
     */
    private int leerId() {
        try {
            return Integer.parseInt(txtId.getText().trim());

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Selecciona un drone o ingresa un ID válido.",
                    exception);
        }
    }

    /**
     * Copia los datos de un drone a los campos del formulario.
     *
     * @param drone drone que se mostrará; si es {@code null}, no realiza cambios
     */
    private void mostrarDrone(Drone drone) {
        if (drone == null) {
            return;
        }

        txtId.setText(String.valueOf(drone.getId()));
        txtSerial.setText(drone.getSerial());
        txtModelo.setText(drone.getModelo());
        txtPeso.setText(String.valueOf(drone.getPeso()));
        cbTipo.setValue("Seleccionar");
    }

    /** Vacía los campos del formulario y restablece el selector de tipo. */
    private void limpiarFormulario() {
        txtId.clear();
        txtSerial.clear();
        txtModelo.clear();
        txtPeso.clear();
        cbTipo.setValue("Seleccionar");
    }

    /**
     * Muestra un error usando el mensaje de una excepción.
     *
     * @param encabezado título del error
     * @param exception excepción cuyo mensaje se mostrará
     */
    private void mostrarError(String encabezado, Exception exception) {
        mostrarError(encabezado, exception.getMessage());
    }

    /**
     * Muestra una alerta de error.
     *
     * @param encabezado título del error
     * @param mensaje detalle del error
     */
    private void mostrarError(String encabezado, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Gestión de drones");
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Muestra una alerta informativa.
     *
     * @param encabezado título de la información
     * @param mensaje detalle informativo
     */
    private void mostrarInformacion(String encabezado, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Gestión de drones");
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private final EvaluarViabilidadVueloUseCase evaluarViabilidad =
            new EvaluarViabilidadVueloService();
}
