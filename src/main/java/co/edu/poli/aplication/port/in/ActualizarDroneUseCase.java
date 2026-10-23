package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para modificar los datos de un drone existente.
 */
public interface ActualizarDroneUseCase {

    /**
     * Actualiza los datos de un drone existente.
     *
     * @param id identificador del drone que se actualizará
     * @param drone nuevos datos del drone
     * @return drone actualizado o {@code null} si no existe
     */
    Drone actualizarDrone(int id, Drone drone);
}
