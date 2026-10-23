package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para registrar un nuevo drone en el sistema.
 */
public interface CrearDroneUseCase {

    /**
     * Registra un nuevo drone.
     *
     * @param drone datos del drone que se creará
     * @return drone creado con su identificador asignado
     */
    Drone crearDrone(Drone drone);
}
