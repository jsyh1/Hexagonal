package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para consultar un drone específico mediante su ID.
 */
public interface ObtenerDronePorIdUseCase {

    /**
     * Busca un drone mediante su identificador.
     *
     * @param id identificador del drone
     * @return drone encontrado o {@code null} si no existe
     */
    Drone obtenerDronePorId(int id);
}
