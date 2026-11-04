package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Puerto de entrada para ejecutar una misión de un drone.
 */
public interface ExecuteMissionUseCase {

    /**
     * Ejecuta la misión coordinando los sistemas del drone.
     *
     * @param drone drone que realizará la misión
     * @return resumen de la operación
     */
    String ejecutarMision(Drone drone);
}