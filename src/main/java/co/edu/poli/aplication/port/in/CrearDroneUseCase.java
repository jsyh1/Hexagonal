package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para registrar un nuevo drone en el sistema.
 */
public interface CrearDroneUseCase {
    Drone crearDrone(Drone drone);
}
