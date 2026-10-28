package co.edu.poli.infrastructure.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.strategy.AltoRendimientoStrategy;
import co.edu.poli.aplication.domain.strategy.AhorroStrategy;
import co.edu.poli.aplication.domain.strategy.ConsumoStrategy;
import co.edu.poli.aplication.domain.strategy.NormalStrategy;
import co.edu.poli.aplication.domain.strategy.PuntoGeografico;
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
import co.edu.poli.aplication.usecase.CalcularConsumoUseCase;
import co.edu.poli.aplication.weather.WeatherNotificationService;
import co.edu.poli.infrastructure.loader.JsonRutaLoader;
import co.edu.poli.infrastructure.persistence.MysqlDroneRepository;
import co.edu.poli.infrastructure.util.HaversineUtil;
import co.edu.poli.infrastructure.weather.OpenMeteoGeocodingAdapter;
import co.edu.poli.infrastructure.weather.OpenMeteoWeatherAdapter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * Controlador de la interfaz JavaFX de gestión de drones,
 * consulta meteorológica y cálculo de consumo energético.
 */
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

    // Controles para el cálculo de consumo energético.

    @FXML
    private ComboBox<Drone> cbDroneConsumo;

    @FXML
    private Label lblRuta;

    @FXML
    private Label lblDistancia;

    @FXML
    private Label lblEstrategia;

    @FXML
    private Label lblConsumo;

    // Dependencias para la carga de rutas y el patrón Strategy.

    private final JsonRutaLoader jsonRutaLoader =
            new JsonRutaLoader();

    private final CalcularConsumoUseCase calcularConsumoUseCase =
            new CalcularConsumoUseCase();

    private PuntoGeografico origenCargado;

    private PuntoGeografico destinoCargado;

    private Double distanciaKm;

    private ConsumoStrategy estrategiaSeleccionada;

    // Dependencias existentes para la gestión de drones.

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

    private final EvaluarViabilidadVueloUseCase evaluarViabilidad =
            new EvaluarViabilidadVueloService();

    // Servicio existente para las notificaciones meteorológicas.

    private final WeatherNotificationService weatherService =
            crearWeatherNotificationService();

    /**
     * Crea el servicio que administra las suscripciones meteorológicas.
     *
     * @return servicio de notificaciones
     */
    private WeatherNotificationService crearWeatherNotificationService() {
        return new WeatherNotificationService(consultarClima);
    }

    /**
     * Configura los adaptadores meteorológicos.
     *
     * @return caso de uso para consultar el clima
     */
    private ConsultarClimaUseCase crearConsultarClimaService() {

        GeocodingPort geocoding =
                new OpenMeteoGeocodingAdapter();

        WeatherApiPort weatherApi =
                new OpenMeteoWeatherAdapter();

        return new ConsultarClimaService(geocoding, weatherApi);
    }

    /**
     * Inicializa los controles de la interfaz.
     */
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

        // Mostrar información legible al seleccionar un dron.
        cbDroneConsumo.setConverter(new StringConverter<Drone>() {

            @Override
            public String toString(Drone drone) {
                if (drone == null) {
                    return "";
                }

                return drone.getSerial()
                        + " - " + drone.getModelo()
                        + " (ID " + drone.getId() + ")";
            }

            @Override
            public Drone fromString(String texto) {
                return null;
            }
        });

        // Actualizar el resultado cuando se cambia el dron.
        cbDroneConsumo.valueProperty().addListener(
                (observable, anterior, seleccionado) ->
                        actualizarResultado());

        if (!cbTipo.getItems().contains("Seleccionar")) {
            cbTipo.getItems().add(0, "Seleccionar");
        }

        cbTipo.setValue("Seleccionar");

        // Ciudades existentes para consultar el clima.
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
        actualizarResultado();
    }

    /**
     * Limpia el formulario para registrar un dron.
     */
    @FXML
    private void nuevoDron() {
        limpiarFormulario();
        tblDrones.getSelectionModel().clearSelection();
    }

    /**
     * Registra un dron con los datos del formulario.
     */
    @FXML
    private void guardarDron() {
        try {
            crearDrone.crearDrone(leerDrone(0));
            cargarDrones();
            limpiarFormulario();

        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo guardar el dron", exception);
        }
    }

    /**
     * Actualiza los datos del dron seleccionado.
     */
    @FXML
    private void editarDron() {
        try {
            int id = leerId();

            Drone actualizado =
                    actualizarDrone.actualizarDrone(id, leerDrone(id));

            if (actualizado == null) {
                mostrarError(
                        "No se encontró el dron",
                        "Verifica el ID seleccionado.");
                return;
            }

            cargarDrones();
            limpiarFormulario();

        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo actualizar el dron", exception);
        }
    }

    /**
     * Elimina el dron seleccionado.
     */
    @FXML
    private void eliminarDron() {
        try {
            Drone seleccionado =
                    tblDrones.getSelectionModel().getSelectedItem();

            int id = leerId();

            eliminarDrone.eliminarDrone(id);

            if (seleccionado != null) {
                weatherService.darDeBaja(seleccionado);
            }

            cargarDrones();
            limpiarFormulario();

        } catch (IllegalArgumentException | IllegalStateException exception) {
            mostrarError("No se pudo eliminar el dron", exception);
        }
    }

    /**
     * Suscribe el dron seleccionado a las notificaciones meteorológicas.
     */
    @FXML
    private void suscribirDrone() {

        Drone seleccionado =
                tblDrones.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError(
                    "Suscripción meteorológica",
                    "Selecciona un dron de la tabla.");
            return;
        }

        weatherService.suscribir(seleccionado);

        mostrarInformacion(
                "Suscripción realizada",
                "El dron " + seleccionado.getSerial()
                        + " recibirá las notificaciones meteorológicas.");
    }

    /**
     * Cancela la suscripción meteorológica del dron seleccionado.
     */
    @FXML
    private void desuscribirDrone() {

        Drone seleccionado =
                tblDrones.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError(
                    "Suscripción meteorológica",
                    "Selecciona un dron de la tabla.");
            return;
        }

        weatherService.darDeBaja(seleccionado);

        mostrarInformacion(
                "Suscripción eliminada",
                "El dron " + seleccionado.getSerial()
                        + " ya no recibirá notificaciones meteorológicas.");
    }

    /**
     * Consulta el clima, evalúa la viabilidad del vuelo y notifica
     * a los drones suscritos.
     */
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

            String resultado =
                    evaluarViabilidad.evaluarViabilidad(ciudad, clima);

            weatherService.notificar(clima, resultado);

            mostrarInformacion(
                    "Viabilidad de vuelo en " + ciudad,
                    resultado
                            + "\n\nSe notificó a los drones suscritos.");

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

    // =========================================================
    // FUNCIONALIDAD NUEVA: CONSUMO ENERGÉTICO
    // =========================================================

    /**
     * Abre el selector de archivos y carga el origen y destino
     * desde un archivo JSON.
     */
    @FXML
    private void cargarRutaJson() {

        FileChooser selector = new FileChooser();

        selector.setTitle("Seleccionar ruta JSON");

        selector.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Archivos JSON", "*.json"));

        File archivo = selector.showOpenDialog(
                tblDrones.getScene().getWindow());

        if (archivo == null) {
            return;
        }

        try {
            List<PuntoGeografico> puntos =
                    jsonRutaLoader.cargar(archivo);

            PuntoGeografico origen = puntos.get(0);
            PuntoGeografico destino = puntos.get(1);

            double nuevaDistancia =
                    HaversineUtil.calcularDistanciaKm(
                            origen, destino);

            // Actualizar el estado solo si la ruta es válida.
            origenCargado = origen;
            destinoCargado = destino;
            distanciaKm = nuevaDistancia;

            actualizarResultado();

        } catch (IOException | IllegalArgumentException exception) {
            mostrarError(
                    "No se pudo cargar la ruta JSON",
                    exception);
        }
    }

    /**
     * Permite seleccionar una estrategia energética.
     */
    @FXML
    private void seleccionarEstrategia() {

        List<String> opciones = List.of(
                "Modo Ahorro",
                "Modo Normal",
                "Modo Alto Rendimiento");

        String estrategiaActual =
                estrategiaSeleccionada == null
                        ? "Modo Normal"
                        : estrategiaSeleccionada.getNombre();

        ChoiceDialog<String> dialogo =
                new ChoiceDialog<>(estrategiaActual, opciones);

        dialogo.setTitle("Estrategia de consumo energético");
        dialogo.setHeaderText(
                "Selecciona la estrategia para el recorrido");
        dialogo.setContentText("Estrategia:");

        Optional<String> seleccion = dialogo.showAndWait();

        seleccion.ifPresent(nombre -> {
            estrategiaSeleccionada = crearEstrategia(nombre);

            calcularConsumoUseCase.setStrategy(
                    estrategiaSeleccionada);

            actualizarResultado();
        });
    }

    /**
     * Crea la estrategia concreta solicitada.
     *
     * @param nombre nombre de la estrategia
     * @return estrategia seleccionada
     */
    private ConsumoStrategy crearEstrategia(String nombre) {

        return switch (nombre) {
            case "Modo Ahorro" ->
                    new AhorroStrategy();

            case "Modo Normal" ->
                    new NormalStrategy();

            case "Modo Alto Rendimiento" ->
                    new AltoRendimientoStrategy();

            default -> throw new IllegalArgumentException(
                    "La estrategia seleccionada no existe.");
        };
    }

    /**
     * Actualiza los datos de la ruta, la estrategia y el consumo
     * estimado que aparecen en la pantalla.
     */
    private void actualizarResultado() {

        if (origenCargado == null || destinoCargado == null
                || distanciaKm == null) {

            lblRuta.setText("Ruta: carga un archivo JSON");
            lblDistancia.setText("Distancia: -- km");

        } else {
            lblRuta.setText(
                    "Ruta: " + origenCargado.getNombre()
                            + " → " + destinoCargado.getNombre());

            lblDistancia.setText(
                    String.format(
                            Locale.US,
                            "Distancia: %.2f km",
                            distanciaKm));
        }

        if (estrategiaSeleccionada == null) {
            lblEstrategia.setText(
                    "Estrategia: no seleccionada");
        } else {
            lblEstrategia.setText(
                    "Estrategia: "
                            + estrategiaSeleccionada.getNombre());
        }

        if (distanciaKm == null) {
            lblConsumo.setText(
                    "Consumo estimado: carga una ruta JSON.");
            return;
        }

        if (estrategiaSeleccionada == null) {
            lblConsumo.setText(
                    "Consumo estimado: selecciona una estrategia.");
            return;
        }

        Drone drone = cbDroneConsumo.getValue();

        if (drone == null) {
            lblConsumo.setText(
                    "Consumo estimado: selecciona un dron.");
            return;
        }

        double consumo =
                calcularConsumoUseCase.calcularConsumo(distanciaKm);

        lblConsumo.setText(
                String.format(
                        Locale.US,
                        "Dron: %s (%s). Consumo estimado: %.2f unidades.%n"
                                + "Cálculo: %.2f km × %.1f = %.2f.",
                        drone.getSerial(),
                        drone.getModelo(),
                        consumo,
                        distanciaKm,
                        estrategiaSeleccionada.getFactor(),
                        consumo));
    }

    // =========================================================
    // FUNCIONES EXISTENTES PARA LA GESTIÓN DE DRONES
    // =========================================================

    /**
     * Carga los drones tanto en la tabla como en el selector de consumo.
     */
    private void cargarDrones() {

        try {
            List<Drone> drones = listarDrones.listarDrones();

            tblDrones.setItems(
                    FXCollections.observableArrayList(drones));

            cbDroneConsumo.setItems(
                    FXCollections.observableArrayList(drones));

        } catch (IllegalStateException exception) {
            mostrarError(
                    "No se pudieron cargar los drones",
                    exception);
        }
    }

    /**
     * Construye un dron con los datos del formulario.
     *
     * @param id identificador del dron
     * @return dron construido
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
                    "El peso debe ser un número válido.",
                    exception);
        }
    }

    /**
     * Lee el identificador del formulario.
     *
     * @return identificador del dron
     */
    private int leerId() {

        try {
            return Integer.parseInt(txtId.getText().trim());

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Selecciona un dron o ingresa un ID válido.",
                    exception);
        }
    }

    /**
     * Muestra en el formulario los datos del dron seleccionado.
     *
     * @param drone dron seleccionado
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

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarFormulario() {

        txtId.clear();
        txtSerial.clear();
        txtModelo.clear();
        txtPeso.clear();

        cbTipo.setValue("Seleccionar");
    }

    /**
     * Muestra un error obtenido de una excepción.
     *
     * @param encabezado título del mensaje
     * @param exception excepción producida
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
        alerta.setContentText(
                mensaje == null ? "Ocurrió un error inesperado." : mensaje);

        alerta.showAndWait();
    }

    /**
     * Muestra una alerta informativa.
     *
     * @param encabezado título de la información
     * @param mensaje detalle de la información
     */
    private void mostrarInformacion(
            String encabezado,
            String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Gestión de drones");
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}