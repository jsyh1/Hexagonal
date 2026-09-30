package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para modificar los datos de un drone existente.
 */
public interface ActualizarDroneUseCase {
    Drone actualizarDrone(int id, Drone drone);
}
