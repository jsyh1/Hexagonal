package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Caso de uso para consultar un drone específico mediante su ID.
 */
public interface ObtenerDronePorIdUseCase {
    Drone obtenerDronePorId(int id);
}
