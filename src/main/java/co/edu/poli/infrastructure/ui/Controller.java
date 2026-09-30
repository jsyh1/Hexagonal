package co.edu.poli.infrastructure.ui;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ActualizarDroneUseCase;
import co.edu.poli.aplication.port.in.CrearDroneUseCase;
import co.edu.poli.aplication.port.in.EliminarDroneUseCase;
import co.edu.poli.aplication.port.in.ListarDronesUseCase;
import co.edu.poli.aplication.servicios.ActualizarDroneService;
import co.edu.poli.aplication.servicios.CrearDroneService;
import co.edu.poli.aplication.servicios.EliminarDroneService;
import co.edu.poli.aplication.servicios.ListarDronesService;
import co.edu.poli.aplication.port.out.DroneRepository;
import co.edu.poli.infrastructure.persistence.MysqlDroneRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

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
	private TableView<Drone> tblDrones;
	@FXML
	private TableColumn<Drone, Integer> colId;
	@FXML
	private TableColumn<Drone, String> colSenal;
	@FXML
	private TableColumn<Drone, String> colModelo;
	@FXML
	private TableColumn<Drone, Double> colPeso;

	private final DroneRepository droneRepository = new MysqlDroneRepository();
	private final CrearDroneUseCase crearDrone = new CrearDroneService(droneRepository);
	private final ActualizarDroneUseCase actualizarDrone = new ActualizarDroneService(droneRepository);
	private final EliminarDroneUseCase eliminarDrone = new EliminarDroneService(droneRepository);
	private final ListarDronesUseCase listarDrones = new ListarDronesService(droneRepository);

	@FXML
	private void initialize() {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colSenal.setCellValueFactory(new PropertyValueFactory<>("serial"));
		colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		txtId.setEditable(false);

		if (!cbTipo.getItems().contains("Seleccionar")) {
			cbTipo.getItems().add(0, "Seleccionar");
		}
		cbTipo.setValue("Seleccionar");

		tblDrones.getSelectionModel().selectedItemProperty().addListener(
				(observable, anterior, seleccionado) -> mostrarDrone(seleccionado));
		cargarDrones();
	}

	@FXML
	private void nuevoDron() {
		limpiarFormulario();
		tblDrones.getSelectionModel().clearSelection();
	}

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

	@FXML
	private void editarDron() {
		try {
			int id = leerId();
			Drone actualizado = actualizarDrone.actualizarDrone(id, leerDrone(id));
			if (actualizado == null) {
				mostrarError("No se encontró el drone", "Verifica el ID seleccionado.");
				return;
			}
			cargarDrones();
			limpiarFormulario();
		} catch (IllegalArgumentException | IllegalStateException exception) {
			mostrarError("No se pudo actualizar el drone", exception);
		}
	}

	@FXML
	private void eliminarDron() {
		try {
			eliminarDrone.eliminarDrone(leerId());
			cargarDrones();
			limpiarFormulario();
		} catch (IllegalArgumentException | IllegalStateException exception) {
			mostrarError("No se pudo eliminar el drone", exception);
		}
	}

	private void cargarDrones() {
		try {
			tblDrones.setItems(FXCollections.observableArrayList(listarDrones.listarDrones()));
		} catch (IllegalStateException exception) {
			mostrarError("No se pudieron cargar los drones", exception);
		}
	}

	private Drone leerDrone(int id) {
		String serial = txtSerial.getText().trim();
		String modelo = txtModelo.getText().trim();
		String pesoTexto = txtPeso.getText().trim();

		if (serial.isEmpty() || modelo.isEmpty() || pesoTexto.isEmpty()) {
			throw new IllegalArgumentException("Completa serial, modelo y peso.");
		}

		try {
			double peso = Double.parseDouble(pesoTexto);
			return new Drone(id, serial, modelo, "", peso);
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("El peso debe ser un número válido.", exception);
		}
	}

	private int leerId() {
		try {
			return Integer.parseInt(txtId.getText().trim());
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException("Selecciona un drone o ingresa un ID válido.", exception);
		}
	}

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

	private void limpiarFormulario() {
		txtId.clear();
		txtSerial.clear();
		txtModelo.clear();
		txtPeso.clear();
		cbTipo.setValue("Seleccionar");
	}

	private void mostrarError(String encabezado, Exception exception) {
		mostrarError(encabezado, exception.getMessage());
	}

	private void mostrarError(String encabezado, String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle("Gestión de drones");
		alerta.setHeaderText(encabezado);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}

}
