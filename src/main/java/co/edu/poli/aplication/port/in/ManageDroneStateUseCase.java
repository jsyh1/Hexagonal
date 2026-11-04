package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/** Puerto de entrada para administrar el ciclo operativo de los drones. */
public interface ManageDroneStateUseCase {

    /** Registra un dron nuevo con estado APAGADO. */
    void registrarDrone(Drone drone);

    /** Elimina el estado en memoria de un dron eliminado. */
    void eliminarDrone(Drone drone);

    void encender(Drone drone);

    void apagar(Drone drone);

    void despegar(Drone drone);

    void iniciarAterrizaje(Drone drone);

    void finalizarAterrizaje(Drone drone);

    String obtenerEstado(Drone drone);
}
